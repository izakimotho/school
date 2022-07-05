package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.SchoolSubjectDto;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Organization;
import lunna.school.model.SchoolSubject;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.SchoolService;
import lunna.school.service.SchoolSubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/16/22, Monday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SchoolSubjectController {
    @Autowired
    SchoolSubjectService schoolSubjectService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    JwtUtils utils;
    ModelMapper modelMapper = new ModelMapper();

    @PostAuthorize("hasAuthority(\"can_add_school_subject\")")
    @PostMapping("/school_subject")
    public ApiResponse create(@RequestBody SchoolSubject schoolSubject,
                              HttpServletRequest request) {
        SchoolSubject subject;
        if (schoolSubject.getOrganization() == null) {
            Organization organization = schoolService.getSchoolById(utils.getSchoolId(request));
            schoolSubject.setOrganization(organization);
        }
        try {
            subject = schoolSubjectService.saveOrUpdate(schoolSubject);

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                modelMapper.map(subject,SchoolSubjectDto.class),
                "Create",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_school_subject\")")
    @GetMapping("/school_subjects")
    public ApiResponse list(HttpServletRequest request) {
        List<SchoolSubjectDto> schoolSubjects;
        try {
            schoolSubjects = schoolSubjectService.getSubjectBYSchool(utils.getSchoolId(request))
                    .stream()
                    .map(subject -> modelMapper.map(subject, SchoolSubjectDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                schoolSubjects,
                "Subject List",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_school_subject\")")
    @GetMapping("/school_subject/{id}")
    public ApiResponse getById(@PathVariable("id") String id) {
        SchoolSubjectDto schoolSubject;
        try {
            schoolSubject = modelMapper.map(schoolSubjectService.getById(UUID.fromString(id)),SchoolSubjectDto.class);
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                schoolSubject,
                "Subject",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_school_subject\")")
    @PutMapping("/school_subject")
    public ApiResponse update(@RequestBody SchoolSubject schoolSubject) {
        SchoolSubject subject;
        try {
            subject = schoolSubjectService.saveOrUpdate(schoolSubject);

        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
        return new ApiResponse(
                subject,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_school_subject\")")
    @DeleteMapping("/school_subject/{id}")
    public ApiResponse delete(@PathVariable("id") String id) {
        SchoolSubject subject = schoolSubjectService.getById(UUID.fromString(id));
        schoolSubjectService.delete(subject);
        return new ApiResponse(
                null,
                "Delete",
                HttpStatus.OK.value()
        );
    }

}
