package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.SchoolCategory;
import lunna.school.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 5:32 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SchoolCategoryController {

    final IService<SchoolCategory> categoryIService;

    @Autowired
    public SchoolCategoryController(IService<SchoolCategory> categoryIService) {
        this.categoryIService = categoryIService;
    }

    @PostAuthorize("hasAuthority(\"can_view_school_category\")")
    @GetMapping("/schools/categories")
    public ApiResponse getSchoolCategory() {

        return new ApiResponse(
                categoryIService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }
}
