package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.ExamTypeDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ExamType;
import lunna.school.model.User;
import lunna.school.service.ExamTypeService;
import lunna.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 4:46 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ExamTypeController {

    final UserService userService;

    final ExamTypeService examTypesService;

    @Autowired
    public ExamTypeController(UserService userService, ExamTypeService examTypesService) {
        this.userService = userService;
        this.examTypesService = examTypesService;
    }


    @PostAuthorize("hasAuthority(\"can_view_exam_type\")")
    @GetMapping("/exam_types")
    public ApiResponse listAllExamTypes(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        List<ExamTypeDto> examTypes;
        try {
            examTypes = examTypesService.listAllTypes();
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                examTypes,
                "Exam Types List",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_type\")")
    @GetMapping("/school/exam_types")
    public ApiResponse listExamTypesBySchool(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }

        List<ExamType> examTypes;
        try {
            examTypes = examTypesService.fetchAll(user.getOrganization());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                examTypes,
                "Exam Types List",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_type\")")
    @GetMapping("/exam_type/{exam_type_id}")
    public ApiResponse listExamTypesById(@PathVariable String exam_type_id) {
        ExamType examType = examTypesService.getById(UUID.fromString(exam_type_id));
        if (examType == null) {
            throw new RecordNotFoundException("Exam Type not found");
        }

        return new ApiResponse(
                examType,
                "Exam Types",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_exam_type\")")
    @PostMapping("/exam_type/create")
    public ApiResponse createExamType(@RequestBody ExamType examType,
                                      Principal principal,
                                      BindingResult result) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        if (examType.getOrganization() == null) {
            examType.setOrganization(user.getOrganization());
        }

        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        ExamType examTypes;

        try {
            examTypes = examTypesService.saveOrUpdate(examType);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                examTypes,
                "Exam Types created",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_exam_type\")")
    @PutMapping("/exam_type/update")
    public ApiResponse updateExamType(@RequestBody ExamType examType,
                                      Principal principal,
                                      BindingResult result) {
        User user = userService.findByUsername(principal.getName());
        ExamType examType1 = examTypesService.getById(examType.getExam_type_id());

        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }

        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }

        examType1.setExam_type_name(examType.getExam_type_name());
        examType1.setDescription(examType.getDescription());
        ExamType examTypes;

        try {
            examTypes = examTypesService.saveOrUpdate(examType1);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                examTypes,
                "Exam Types updated",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_exam_type\")")
    @DeleteMapping("/exam_type/{exam_type_id}/delete")
    public ApiResponse delete(@PathVariable String exam_type_id) {
        ExamType examType = examTypesService.getById(UUID.fromString(exam_type_id));
        if (examType == null) {
            throw new RecordNotFoundException("Exam Type no found");
        }
        examTypesService.delete(examType);
        return new ApiResponse(
                null,
                "Exam Types delete",
                HttpStatus.OK.value()
        );
    }

}
