package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.model.ClassModel;
import lunna.school.model.Organization;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.ClassService;
import lunna.school.service.SchoolService;
import lunna.school.service.impl.ClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * ClassController
 *
 * @author Collins K. Sang
 * 2021/07/02 13:42
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class ClassController {
    final ClassService classService;
    @Autowired
    SchoolService schoolService;


    final ClassService classServiceimp;
    @Autowired
    JwtUtils utils;
    @Autowired
    public ClassController(ClassServiceImpl classService, ClassService classServiceimp) {
        this.classService = classService;
        this.classServiceimp = classServiceimp;
    }

    @PostAuthorize("hasAuthority(\"can_view_class_model\")")
    @GetMapping("/classes/list")
    public ApiResponse getAllClasses(HttpServletRequest request) {
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));

        List<ClassModel> classModels = null;

        try {
            if (request.isUserInRole("ROLE_Super Admin")){
                 classModels = classService.listAll();
            }else {
                classModels = classService.getByEducationAndSchoolLevel(
                        org.getEducation_system().getEducation_system_id(),
                        org.getSchool_level().getSchool_level_id());
            }

        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }


        return new ApiResponse(
                classModels,
                "",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_class_model\")")
    @GetMapping("/classes/{class_id}")
    public ApiResponse getClassById(@PathVariable String class_id) {
        return new ApiResponse(
                classService.getById(UUID.fromString(class_id)),
                "",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_add_class_model\")")
    @PostMapping("/classes/create")
    public ApiResponse createClasses(@Valid @RequestBody ClassModel classModel, BindingResult result)  {
        if (result.hasErrors()){

            throw new BadRequestException(result.getAllErrors().toString());
        }
        return new ApiResponse(
                classService.saveOrUpdate(classModel),
                "Class saved successfully",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_edit_class_model\")")
    @PutMapping("/classes/update")
    public ApiResponse updateClasses(@Valid @RequestBody ClassModel classModel,
                                      BindingResult result) {
        if (result.hasErrors()){

            throw new BadRequestException(result.getAllErrors().toString());
        }
        ClassModel classModel1 = classService.getById(classModel.getClass_id());
        ClassModel classModel2 = new ClassModel(classModel1.getClass_id(), classModel.getClass_name(), classModel.getDescription(), classModel.getEducation_system(), classModel.getSchool_level(), classModel.getCreateAt());
        return new ApiResponse(
                classService.saveOrUpdate(classModel2),
                "Class updated successfully",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_class_model\")")
    @DeleteMapping("/classes/{class_id}/delete")
    public ApiResponse deleteClasses(@PathVariable("class_id") String class_id) {
        ClassModel classModel1 = classService.getById(UUID.fromString(class_id));
        classService.delete(classModel1);
        return new ApiResponse(
                null,
                "Class deleted successfully",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_class_model\")")
    @PostMapping("/classes/filter")
    public ApiResponse getClasses(@RequestBody ClassModel classModel) {
        return new ApiResponse(
                classServiceimp.filterClasses(classModel),
                "",
                HttpStatus.OK.value());
    }


}
