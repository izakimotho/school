package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.SchoolLevel;
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
 * Created : 01. Jul 2021 8:34 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SchoolLevelController {

    final IService<SchoolLevel> levelIService;

    @Autowired
    public SchoolLevelController(IService<SchoolLevel> levelIService) {
        this.levelIService = levelIService;
    }

    @PostAuthorize("hasAuthority(\"can_view_school_level\")")
    @GetMapping("/schools/levels")
    public ApiResponse getSchoolLevel() {

        return new ApiResponse(
                levelIService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }
}
