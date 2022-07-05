package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.SchoolGender;
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
public class SchoolGenderController {

    final IService<SchoolGender> genderIService;

    @Autowired
    public SchoolGenderController(IService<SchoolGender> genderIService) {
        this.genderIService = genderIService;
    }

//    @PostAuthorize("hasAuthority(\"can_view_school_gender\")")
    @GetMapping("/schools/gender")
    public ApiResponse getSchoolSponsor() {

        return new ApiResponse(
                genderIService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }
}
