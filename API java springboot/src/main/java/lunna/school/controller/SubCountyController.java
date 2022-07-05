package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.service.SubCountyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 1:00 PM
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class SubCountyController {
    final SubCountyService subCountyIService;

    public SubCountyController(SubCountyService subCountyIService) {
        this.subCountyIService = subCountyIService;
    }

    @GetMapping("/sub_counties")
    public ApiResponse getSubCounties() {

        return new ApiResponse(
                subCountyIService.listAll(),
                "",
                HttpStatus.OK.value()
            );
    }

    @GetMapping("/county/{county_code}/sub_county")
    public ApiResponse getSubCounty(@PathVariable("county_code") Long county_code) {
        return new ApiResponse(
                subCountyIService.getByCode(county_code),
                "",
                HttpStatus.OK.value()
        );
    }
}
