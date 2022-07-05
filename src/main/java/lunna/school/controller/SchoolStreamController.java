package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.SchoolStreamDto;
import lunna.school.dto.StreamDetailsDto;
import lunna.school.dto.StudentDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 05. Jul 2021 9:08 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class SchoolStreamController {
    ModelMapper modelMapper = new ModelMapper();
    final
    SchoolStreamService schoolStreamService;

    final UserService userService;

    final SchoolService schoolService;
    @Autowired
    StaffService staffService;
    @Autowired
    StreamDetailsService streamDetailsService;
    @Autowired
    StudentsService studentsService;
    @Autowired
    JwtUtils utils;

    @Autowired
    public SchoolStreamController(SchoolStreamService schoolStreamService, UserService userService, SchoolService schoolService) {
        this.schoolStreamService = schoolStreamService;
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @PostAuthorize("hasAuthority(\"can_view_school_stream\")")
    @GetMapping("/schools/streams")
    public ApiResponse getStream(HttpServletRequest request) {
        List<SchoolStreamDto> SchoolStreamList = new ArrayList<>();
        if (request.isUserInRole("ROLE_Super Admin")) {
            SchoolStreamList = schoolStreamService.listAll()
                    .stream()
                    .map(stream -> modelMapper.map(stream, SchoolStreamDto.class))
                    .collect(Collectors.toList());
        } else {
            SchoolStreamList = schoolStreamService.listBySchoolId(utils.getSchoolId(request))
                    .stream()
                    .map(stream -> modelMapper.map(stream, SchoolStreamDto.class))
                    .collect(Collectors.toList());
            ;
        }

        return new ApiResponse(
                SchoolStreamList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_school_stream\")")
    @PostMapping("/schools/streams/create")
    public ApiResponse streamCreate(@RequestBody SchoolStream schoolStream,
                                    BindingResult result,
                                    Principal principal) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        SchoolStream schoolStream1 = null;

        if (schoolStream.getOrganization() == null) {
            try {
                User user = userService.findByUsername(principal.getName());
                schoolStream.setOrganization(user.getOrganization());
            } catch (Exception e) {
                throw new RecordNotFoundException(e.getMessage());
            }
        }

        try {
            schoolStream1 = schoolStreamService.saveOrUpdate(schoolStream);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                schoolStream1,
                "Stream added",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_view_school_stream\")")
    @GetMapping("/schools/streams/{school_stream_id}")
    public ApiResponse getStream(@PathVariable String school_stream_id) {
        SchoolStream schoolStream = schoolStreamService.getById(UUID.fromString(school_stream_id));
        return new ApiResponse(
                schoolStream,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_school_stream\")")
    @PutMapping("/schools/streams/update")
    public ApiResponse streamUpdate(@RequestBody SchoolStream schoolStream,
                                    BindingResult result,
                                    Principal principal) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        SchoolStream schoolStream1 = schoolStreamService.getById(schoolStream.getSchool_stream_id());

        if (schoolStream1.getOrganization() == null) {
            try {
                schoolStream1.setOrganization(schoolStream.getOrganization());
            } catch (Exception e) {
                throw new RecordNotFoundException(e.getMessage());
            }
        }
        schoolStream1.setStream_name(schoolStream.getStream_name());
        schoolStream1.setDescription(schoolStream.getDescription());
        schoolStream1.setAbbr(schoolStream.getAbbr());

        return new ApiResponse(
                schoolStreamService.saveOrUpdate(schoolStream1),
                "Stream Updated",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_view_school_stream\")")
    @GetMapping("/schools/streams/{school_stream_id}/details")
    public ApiResponse getStreamClassDetails(@PathVariable String school_stream_id) {
        List<StreamDetailsDto> streamDetailsDtos;
        try {
            streamDetailsDtos = streamDetailsService.getByClassStream(UUID.fromString(school_stream_id))
                    .stream()
                    .map(stream -> modelMapper.map(stream, StreamDetailsDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                streamDetailsDtos,
                "StreamDetails List",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_school_stream\")")
    @GetMapping("/schools/streams/{school_id}/school")
    public ApiResponse getSchoolStreamBySchoolId(@PathVariable String school_id) {
        List<StreamDetailsDto> streamDetailsDtos;
        try {
            streamDetailsDtos = schoolStreamService.listBySchoolId(UUID.fromString(school_id))
                    .stream()
                    .map(stream -> modelMapper.map(stream, StreamDetailsDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
        return new ApiResponse(
                streamDetailsDtos,
                "School Stream",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_school_stream\")")
    @PostMapping("/schools/streams/{school_stream_id}/assign/teacher")
    public ApiResponse assignTeacher(@RequestBody Staff staff, @PathVariable String school_stream_id) {
        SchoolStream schoolStream = schoolStreamService.getById(UUID.fromString(school_stream_id));
        if (staff == null) {
            throw new RecordNotFoundException(
                    "Staff should not be null"
            );
        }
        Staff staff1 = staffService.getStaffById(staff.getStaff_id());
        if (!staff1.getOrganization().equals(schoolStream.getOrganization())) {
            throw new BadRequestException("Staff assigned does not belong to :" + schoolStream.getOrganization().getOrg_name());

        }
        schoolStream.setClass_teacher(staff);
        SchoolStream schoolStream1;
        try {
            schoolStream1 = schoolStreamService.schoolStreamUpdate(schoolStream);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                schoolStream1,
                "School Class Assign;",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_school_stream_students\")")
    @GetMapping("/schools/streams/{school_stream_id}/students")
    public ApiResponse getSchoolStreamStudents(@PathVariable String school_stream_id) {
        SchoolStream schoolStream = schoolStreamService.getById(UUID.fromString(school_stream_id));
        List<StudentDto> studentDtoList;
        try {
            studentDtoList = schoolStream.getStudentList().stream()
                    .map(e -> modelMapper.map(e, StudentDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }


        return new ApiResponse(
                studentDtoList,
                "School Stream",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_school_stream_rep\")")
    @PostMapping("/schools/streams/{school_stream_id}/assign/class_rep")
    public ApiResponse assignClassRep(@RequestBody Student student, @PathVariable String school_stream_id) {
        SchoolStream schoolStream = schoolStreamService.getById(UUID.fromString(school_stream_id));
        if (student == null) {
            throw new RecordNotFoundException(
                    "Student should not be null"
            );
        }
        Student student1 = studentsService.getStudentById(student.getStudent_id());
        if (!student1.getSchool_stream().getOrganization().getOrg_id().equals(schoolStream.getOrganization().getOrg_id())) {
            throw new BadRequestException("Student does not belong to: " + schoolStream.getOrganization().getOrg_name());
        }
        schoolStream.setClass_rep(student);

        SchoolStream schoolStream1;
        try {
            schoolStream1 = schoolStreamService.schoolStreamUpdate(schoolStream);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                schoolStream1,
                "School Stream Rep Assigned ;",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_school_stream\")")
    @DeleteMapping("/schools/streams/{school_stream_id}/delete")
    public ApiResponse deleteStream(@PathVariable String school_stream_id) {
        SchoolStream schoolStream = schoolStreamService.getById(UUID.fromString(school_stream_id));
        schoolStreamService.softDelete(schoolStream);
        return new ApiResponse(
                null,
                "Stream Deleted",
                HttpStatus.OK.value()
        );
    }
}
