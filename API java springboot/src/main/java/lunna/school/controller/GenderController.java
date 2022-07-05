package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * IntelliJ IDEA
 * school
 * GenderController
 *
 * @author Collins K. Sang
 * 2021/07/21 12:10
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GenderController {
    final GenderService genderService;

    @Autowired
    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping("/users/gender")
    public ApiResponse getUserTypes(Principal principal, Authentication authentication) {
        return new ApiResponse(
                genderService.getGenderTypes(),
               "",
                HttpStatus.OK.value()
        );
    }
}
