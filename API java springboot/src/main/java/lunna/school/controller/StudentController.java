package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.SchoolStreamDto;
import lunna.school.dto.StudentDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.LockedException;
import lunna.school.exception.NoContentException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.HostelService;
import lunna.school.service.SchoolService;
import lunna.school.service.StudentsService;
import lunna.school.service.UserService;
import lunna.school.service.impl.ClassServiceImpl;
import lunna.school.service.impl.PositionServiceImpl;
import lunna.school.service.impl.SchoolStreamServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * StudentController
 *
 * @author Collins K. Sang
 * 2021/06/29 09:05
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class StudentController {

    final StudentsService studentsService;

    final UserService userService;
    final SchoolService schoolService;
    final ClassServiceImpl classService;
    final HostelService hostelService;
    final SchoolStreamServiceImpl schoolStreamServiceImpl;
    final PositionServiceImpl positionServiceImpl;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    JwtUtils utils;

    @Autowired
    public StudentController(StudentsService studentsService,
                             UserService userService, SchoolService schoolService,
                             ClassServiceImpl classService, HostelService hostelService,
                             SchoolStreamServiceImpl schoolStreamServiceImpl, PositionServiceImpl positionServiceImpl) {
        this.studentsService = studentsService;
        this.userService = userService;
        this.schoolService = schoolService;
        this.classService = classService;
        this.hostelService = hostelService;
        this.schoolStreamServiceImpl = schoolStreamServiceImpl;
        this.positionServiceImpl = positionServiceImpl;
    }

    //create student record
    @PostAuthorize("hasAuthority(\"can_add_student\")")
    @PostMapping("/students/create")
    public ApiResponse createStudent(@RequestBody Student student, Principal principal,
                                     BindingResult result) throws BadRequestException {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        String user = principal.getName();
        User user1 = userService.findByUsername(user);
        student.setCreated_by(user1);
        student.setOrganization(user1.getOrganization());
        checkClass(student);
        //check if hostel has vacancy
        var count = 0;
        var hostelCapacity = 0;
        if (student.getHostel() != null) {
            hostelCapacity = hostelService.getById(student.getHostel().getHostel_id().toString()).getHostel_capacity();
            count = studentsService.countHostelCapacity(student.getHostel());

            if (count < hostelCapacity) {
                int countClassCapacity = 0;
                int getStoredClassCapacity = 0;
                if (student.getSchool_stream() != null) {
                    countClassCapacity = studentsService.countClassCapacity(student.getSchool_stream());
                    getStoredClassCapacity = schoolStreamServiceImpl.getById(student.getSchool_stream().getSchool_stream_id()).getClass_capacity();

                }

                //check if assigned class has vacancy

                if (countClassCapacity >= getStoredClassCapacity) {
                    throw new LockedException("Class room capacity is full. Kindly try another class");

                }
            } else {
                return new ApiResponse(
                        null,
                        "Hostel capacity is full. Kindly try another hostel",
                        HttpStatus.LOCKED.value()
                );
            }

        }

        Student student1 = null;
        try {
            student1 = studentsService.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        StudentDto studentDto = modelMapper.map(student1, StudentDto.class);

        return new ApiResponse(
                studentDto,
                "Student saved successfully",
                HttpStatus.OK.value()
        );

    }

    private void checkClass(@RequestBody Student student) {
        if (student.getSchool_stream() != null) {
            SchoolStream classModel = student.getSchool_stream() != null ?
                    schoolStreamServiceImpl.getById(student.getSchool_stream().getSchool_stream_id()) :
                    null;
            student.setSchool_stream(classModel);
        }
        if (student.getHostel() != null) {
            Hostel hostel = student.getHostel() != null ?
                    hostelService.getById(student.getHostel().getHostel_id().toString()) :
                    null;
            student.setHostel(hostel);
        }

        if (student.getPositions() != null) {
            Positions positions = student.getPositions() != null ?
                    positionServiceImpl.getById(student.getPositions().getPosition_id()) :
                    null;
            student.setPositions(positions);
        }
    }

    //update student record
    @PostAuthorize("hasAuthority(\"can_edit_student\")")
    @PutMapping("/students/update")
    public ApiResponse updateStudent(@RequestBody Student student, Principal principal) {
        String user = principal.getName();
        User user1 = userService.findByUsername(user);

        checkClass(student);

        int count = studentsService.countHostelCapacity(student.getHostel());
        int hostelCapacity = hostelService.getById(student.getHostel().getHostel_id().toString()).getHostel_capacity();

        if (count >= hostelCapacity) {
            throw new LockedException("Hostel capacity is full. Kindly try another hostel");
        }
        //check if assigned class has vacancy
        int countClassCapacity = studentsService.countClassCapacity(student.getSchool_stream());
        int getStoredClassCapacity = schoolStreamServiceImpl.getById(student.getSchool_stream().getSchool_stream_id()).getClass_capacity();

        if (countClassCapacity >= getStoredClassCapacity) {
            throw new LockedException("Class room capacity is full. Kindly try another class");
        }

        Student student1 = studentsService.getStudentById(student.getStudent_id());
        student1.setAdmission_date(student.getAdmission_date());
        student1.setIs_boarder(student.getIs_boarder());
        student1.setDob(student.getDob());
        student1.setFees(student.getFees());
        student1.setGender(student.getGender());
        student1.setFirst_name(student.getFirst_name());
        student1.setMiddle_name(student.getMiddle_name());
        student1.setSurname(student.getSurname());
        student1.setGuardians_name(student.getGuardians_name());
        student1.setGuardian_phone(student.getGuardian_phone());
        student1.setHostel(student.getHostel());
        student1.setKin_phone(student.getKin_phone());
        student1.setKin_relationship(student.getKin_relationship());
        student1.setSchool_stream(student.getSchool_stream());
        student1.setNext_of_kin(student.getNext_of_kin());
        student1.setPositions(student.getPositions());
        student1.setCreated_by(user1);

        Student student2 = studentsService.save(student1);
        StudentDto studentDto = modelMapper.map(student2, StudentDto.class);
        if (student2 != null) {
            return new ApiResponse(
                    studentDto,
                    "Student updated successfully",
                    HttpStatus.OK.value()
            );
        } else {
            throw new NoContentException("Failed to update the record kindly try again");
        }
    }

    //get all students
    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/students/list")
    public ApiResponse getStudents(HttpServletRequest request) {

        List<StudentDto> studentDtoList = studentsService.getStudentsPerSchool(utils.getSchoolId(request))
                .stream().map(
                        s -> new StudentDto(s.getStudent_id(), s.getFirst_name(), s.getMiddle_name(), s.getSurname(),
                                s.getGender(), s.getDob(), s.getGuardians_name(), s.getGuardian_phone(),
                                s.getNext_of_kin(), s.getKin_phone(), s.getKin_relationship(),
                                s.getIs_boarder(), s.getAdmission_date(), s.getAvatar(), s.getBirth_certificate(),
                                modelMapper.map(s.getSchool_stream(), SchoolStreamDto.class), s.getHostel(), s.getFees()
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                studentDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    //get specific student
    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/students/{id}")
    public ApiResponse getStudent(@PathVariable String id) {

        Student student = studentsService.getStudentById(UUID.fromString(id));
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        return new ApiResponse(
                studentDto,
                "",
                HttpStatus.OK.value()
        );
    }

    //get student per school
    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/schools/{school_id}/students")
    public ApiResponse getStudentPerSchool(@PathVariable String school_id) {


//        Organization organization = schoolService.getSchoolById(UUID.fromString(school_id));

        List<StudentDto> studentDtoList = studentsService.getStudentsPerSchool(UUID.fromString(school_id)).stream().map(
                        s -> new StudentDto(s.getStudent_id(), s.getFirst_name(), s.getMiddle_name(), s.getSurname(),
                                s.getGender(), s.getDob(), s.getGuardians_name(), s.getGuardian_phone(),
                                s.getNext_of_kin(), s.getKin_phone(), s.getKin_relationship(),
                                s.getIs_boarder(), s.getAdmission_date(), s.getAvatar(), s.getBirth_certificate(),
                                modelMapper.map(s.getSchool_stream(), SchoolStreamDto.class), s.getHostel(), s.getFees()
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                studentDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    //get student per class
    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/schools/{school_id}/students/level/{level_id}")
    public ApiResponse getStudentPerClass(@PathVariable String school_id, @PathVariable String level_id) {

        List<StudentDto> studentDtoList = studentsService.getStudentsPerSchoolStream(UUID.fromString(school_id), UUID.fromString(level_id)).stream().map(
                        s -> new StudentDto(s.getStudent_id(), s.getFirst_name(), s.getMiddle_name(), s.getSurname(),
                                s.getGender(), s.getDob(), s.getGuardians_name(), s.getGuardian_phone(),
                                s.getNext_of_kin(), s.getKin_phone(), s.getKin_relationship(),
                                s.getIs_boarder(), s.getAdmission_date(), s.getAvatar(), s.getBirth_certificate(),
                                modelMapper.map(s.getSchool_stream(), SchoolStreamDto.class), s.getHostel(), s.getFees()
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                studentDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    //get student per gender
    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/students/gender/{gender}")
    public ApiResponse getStudentPerGender(@PathVariable GenderEnum gender) throws ConversionFailedException {

        List<StudentDto> studentDtoList = studentsService.getStudentsPerGender(gender).stream().map(
                        s -> new StudentDto(s.getStudent_id(), s.getFirst_name(), s.getMiddle_name(), s.getSurname(),
                                s.getGender(), s.getDob(), s.getGuardians_name(), s.getGuardian_phone(),
                                s.getNext_of_kin(), s.getKin_phone(), s.getKin_relationship(),
                                s.getIs_boarder(), s.getAdmission_date(), s.getAvatar(), s.getBirth_certificate(),
                                modelMapper.map(s.getSchool_stream(), SchoolStreamDto.class), s.getHostel(), s.getFees()
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                studentDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_student\")")
    @DeleteMapping("/students/{student_id}/delete")
    public ApiResponse deleteStudent(@PathVariable("student_id") String student_id) {
        Student student = studentsService.getStudentById(UUID.fromString(student_id));
        if (student == null) {
            throw new RecordNotFoundException("Student doesn't exist or has been deleted");
        }
        studentsService.delete(student);
        return new ApiResponse(
                null,
                "Deleted successfully",
                HttpStatus.OK.value()
        );
    }


}
