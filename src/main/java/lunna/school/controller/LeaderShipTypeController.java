package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.service.impl.PositionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 9:16 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LeaderShipTypeController {

    final PositionServiceImpl positionServiceImpl;

    @Autowired
    public LeaderShipTypeController(PositionServiceImpl positionServiceImpl) {
        this.positionServiceImpl = positionServiceImpl;
    }

    @GetMapping("/leadership_types")
    public ApiResponse getTypes() {
        return new ApiResponse(
                positionServiceImpl.getLeadershipTypes(),
                "Flags",
                HttpStatus.OK.value()
        );

    }
}
