package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.service.WardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 1:02 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class WardController {
    final WardService wardIService;

    public WardController(WardService wardIService) {
        this.wardIService = wardIService;
    }

    @GetMapping("/wards")
    public ApiResponse getWards() {
        return new ApiResponse(
                wardIService.listAll(),
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/sub_county/{sub_county_code}/ward")
    public ApiResponse getWards(@PathVariable("sub_county_code") Long sub_county_code) {

        return new ApiResponse(
                wardIService.getByCode(sub_county_code),
                "",
                HttpStatus.OK.value()
        );
    }
}
