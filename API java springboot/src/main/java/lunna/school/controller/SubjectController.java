package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Organization;
import lunna.school.model.Subject;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.SchoolService;
import lunna.school.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 11:13 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class SubjectController {
    final
    SubjectService subjectService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    JwtUtils utils;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostAuthorize("hasAuthority(\"can_add_subject\")")
    @PostMapping("/subjects/create")
    public ApiResponse createSubject(@RequestBody Subject subject,
                                     BindingResult result){
        if(result.hasErrors()){
            throw new BadRequestException(result.getAllErrors().toString());
        }
        Subject subject1;
        try
        {
            subject1 =  subjectService.saveOrUpdate(subject);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subject1,
                "Subject created successfully",
                HttpStatus.OK.value()
        );

    }
    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @GetMapping("/subjects")
    public  ApiResponse getSubjects(){
        List<Subject> subjectList;
        try{
            subjectList =  subjectService.listAll();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectList,
                "Subject list",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_school-subject\")")
    @GetMapping("/school-level-subjects")
    public  ApiResponse getSubjects(HttpServletRequest request){
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));
        List<Subject> subjectList;
        try{
            subjectList =  subjectService.getSubjectsBySchoolLevel(org.getSchool_level().getSchool_level_id());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectList,
                "Subject list",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @GetMapping("/subjects/{subject_id}")
    public  ApiResponse getSubjectById(@PathVariable String subject_id){
        Subject subjectList;
        try{
            subjectList =  subjectService.getById(UUID.fromString(subject_id));
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectList,
                "Subject by id",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @PostMapping("/subjects/filter")
    public ApiResponse getSubjectFilters(@RequestBody Subject subject){
        return new ApiResponse(
                subjectService.filterSubjects(subject),
                "",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_edit_subject\")")
    @PutMapping("/subjects/update")
    public ApiResponse updateSubject(@RequestBody Subject subject,
                                     BindingResult result){
        if(result.hasErrors()){
            throw new BadRequestException(result.getAllErrors().toString());
        }
        Subject subject1 = subjectService.getById(subject.getSubject_id());
        subject1.setSubject_abbr(subject.getSubject_abbr());
        subject1.setSubject_name(subject.getSubject_name());
        subject1.setDescription(subject.getDescription());
        subject1.setEducation_system(subject.getEducation_system());
        subject1.setSchool_level(subject.getSchool_level());
        Subject subjectUpdated;
        try
        {
            subjectUpdated =  subjectService.saveOrUpdate(subject1);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectUpdated,
                "Subject updated successfully",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @GetMapping("/subjects/{subject_id}/details")
    public ApiResponse getSubjectDetails(@PathVariable String subject_id){


        return new ApiResponse(
                subjectService.getSubjectDetails(subject_id),
                "Subject details",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_subject\")")
    @DeleteMapping("/subjects/{subject_id}/delete")
    public ApiResponse deleteSubject(@PathVariable String subject_id){
        Subject subject = subjectService.getById(UUID.fromString(subject_id));
//        subject.setDeletedAt(new Date());
        subjectService.delete(subject);
        return new ApiResponse(
                null,
                "Subject Deleted",
                HttpStatus.OK.value()
        );
    }


    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @GetMapping("/subjects/school_level/{school_level_id}")
    public  ApiResponse getSubjectBySchoolLevel(@PathVariable String school_level_id){
        List<Subject> subjectList;
        try{
            subjectList =  subjectService.getSubjectsBySchoolLevel(UUID.fromString(school_level_id));
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectList,
                "",
                HttpStatus.OK.value()
        );
    }


    @PostAuthorize("hasAuthority(\"can_view_subject\")")
    @GetMapping("/subjects/education_system/{education_system_id}")
    public  ApiResponse getSubjectByEducationSystem(@PathVariable String education_system_id){
        List<Subject> subjectList;
        try{
            subjectList =  subjectService.getSubjectsByEducationSystem(UUID.fromString(education_system_id));
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return new ApiResponse(
                subjectList,
                "Subject by id",
                HttpStatus.OK.value()
        );
    }
}
