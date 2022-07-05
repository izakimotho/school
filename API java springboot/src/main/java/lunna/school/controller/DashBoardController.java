package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.DashBoard;
import lunna.school.model.Organization;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.DashBoardService;
import lunna.school.service.SchoolService;
import lunna.school.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DashBoardController {
    @Autowired
    DashBoardService dashBoardService;
    @Autowired
    SchoolService schoolService;

    @Autowired
    JwtUtils utils;

    @GetMapping("/dashboard")
    public ApiResponse dashboard(HttpServletRequest request) {
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));
        DashBoard dashBoard = new DashBoard();
        dashBoard.setUsers(dashBoardService.getUsers(org.getOrg_id()));
        dashBoard.setStaffs(dashBoardService.getStaffs(org.getOrg_id()));
        dashBoard.setClasses(dashBoardService.getClasses(org.getOrg_id()));
        dashBoard.setStudents(dashBoardService.getStudents(org.getOrg_id()));
        dashBoard.setSubjects(dashBoardService.getSubjects(org.getOrg_id()));

        return new ApiResponse(
                dashBoard,
                "",
                HttpStatus.OK.value()
        );

    }

}
