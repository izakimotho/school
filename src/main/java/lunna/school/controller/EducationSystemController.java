package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.model.EducationSystem;
import lunna.school.service.EducationSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 5:39 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EducationSystemController {
    private final EducationSystemService educationSystemService;
    @Autowired
    public EducationSystemController(EducationSystemService educationSystemService){
        this.educationSystemService = educationSystemService;
    }

    @PostAuthorize("hasAuthority(\"can_add_education_system\")")
    @PostMapping("/education_system/add")
    public ApiResponse create(@Valid @RequestBody EducationSystem educationSystem){
        EducationSystem educationSystem1 = null;
        try{
            educationSystem1 = educationSystemService.create(educationSystem);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                educationSystem1,
                "Education System Created Successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_education_system\")")
    @GetMapping("/education_system/list")
    public ApiResponse getEducationSystems(){
        List<EducationSystem> educationSystem1 = educationSystemService.getList();

        return new ApiResponse(
                educationSystem1,
                "Education System List",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_education_system\")")
    @GetMapping("/education_system/{id}")
    public ApiResponse getEducationSystems(@PathVariable("id") String id){
        EducationSystem educationSystem1 = educationSystemService.getById(UUID.fromString(id));

        return new ApiResponse(
                educationSystem1,
                "Education System ",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_education_system\")")
    @PutMapping("/education_system/")
    public ApiResponse updateEducationSystems(@RequestBody EducationSystem educationSystem){

        EducationSystem educationSystem1 ;
        try {
            educationSystem1 = educationSystemService.update(educationSystem);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                educationSystem1,
                "Education System ",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_education_system\")")
    @DeleteMapping("/education_system/{id}")
    public ApiResponse delete(@PathVariable("id") String id){

        EducationSystem educationSystem1 = educationSystemService.getById(UUID.fromString(id));
        try {
            educationSystemService.delete(educationSystem1);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                null,
                "Education System Deleted ",
                HttpStatus.OK.value()
        );
    }
}
