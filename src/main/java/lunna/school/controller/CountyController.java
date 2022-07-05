package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.County;
import lunna.school.service.IService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:57 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CountyController {
    final IService<County> countyIService;

    public CountyController(IService<County> countyIService) {
        this.countyIService = countyIService;
    }

    @GetMapping("/counties")
    public ApiResponse getCounties() {

        return new ApiResponse(
                countyIService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }

}
