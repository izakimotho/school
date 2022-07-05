package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.SchoolSponsor;
import lunna.school.service.IService;
import lunna.school.service.impl.SchoolSponsorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 8:32 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SchoolSponsorController {
    final IService<SchoolSponsor> schoolSponsorService;
    @Autowired
    public SchoolSponsorController(SchoolSponsorServiceImpl schoolSponsorServiceImpl){
        this.schoolSponsorService = schoolSponsorServiceImpl;

    }

    @PostAuthorize("hasAuthority(\"can_view_school_sponsor\")")
    @GetMapping("/schools/sponsors")
    public ApiResponse getSchoolSponsor(){

        return new ApiResponse(
                schoolSponsorService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }
}
