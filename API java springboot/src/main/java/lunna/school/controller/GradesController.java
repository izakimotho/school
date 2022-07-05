package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.Grades;
import lunna.school.model.Organization;
import lunna.school.model.Staff;
import lunna.school.model.User;
import lunna.school.service.SchoolService;
import lunna.school.service.StaffService;
import lunna.school.service.UserService;
import lunna.school.service.impl.GradesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * GradesController
 *
 * @author Collins K. Sang
 * 2021/07/07 14:19
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GradesController {

    final GradesServiceImpl gradesServiceImpl;
    final UserService userService;
    final StaffService staffService;
    final SchoolService schoolService;

    @Autowired
    public GradesController(GradesServiceImpl gradesServiceImpl, UserService userService, StaffService staffService, SchoolService schoolService) {
        this.gradesServiceImpl = gradesServiceImpl;
        this.userService = userService;
        this.staffService = staffService;
        this.schoolService = schoolService;
    }

    //create grades
    @PostAuthorize("hasAuthority(\"can_add_grades\")")
    @PostMapping("/grades/create")
    public ApiResponse createGrades(@RequestBody Grades grades, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        Organization organization = schoolService.getSchoolById(user.getOrganization().getOrg_id());
        grades.setOrganization(organization);
        grades.setCreated_by(user);
        return new ApiResponse(
                gradesServiceImpl.saveOrUpdate(grades),
                "Grades saved successfully",
                HttpStatus.OK.value()
        );
    }

    //edit grades
    @PostAuthorize("hasAuthority(\"can_edit_grades\")")
    @PutMapping("/grades/{grades_id}/update")
    public ApiResponse updateGrades(@RequestBody Grades grades, Principal principal, @PathVariable("grades_id") String grades_id) {
        Grades grades1 = gradesServiceImpl.getById(UUID.fromString(grades_id));
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        Organization organization = schoolService.getSchoolById(user.getOrganization().getOrg_id());
        grades.setOrganization(organization);
        grades.setCreated_by(user);
        grades1.setGrade_name(grades.getGrade_name());
        grades1.setLower_point(grades.getLower_point());
        grades1.setHigher_point(grades.getHigher_point());
        grades1.setRemarks(grades.getRemarks());

        return new ApiResponse(
                gradesServiceImpl.saveOrUpdate(grades1),
                "Grades updated successfully",
                HttpStatus.OK.value()
        );
    }

    //get all grades

    @PostAuthorize("hasAuthority(\"can_view_grades\")")
    @GetMapping("/grades/list")
    public ApiResponse getGradesList() {
        return new ApiResponse(
                gradesServiceImpl.listAll(),
                "",
                HttpStatus.OK.value()
        );
    }

    //get grade per school
    @PostAuthorize("hasAuthority(\"can_view_grades\")")
    @GetMapping("/school/{school_id}/grades/list")
    public ApiResponse getGradesListPerSchool(@PathVariable("school_id") String school_id) {
        List<Grades> grades = gradesServiceImpl.getPerSchool(UUID.fromString(school_id));
        return new ApiResponse(
                grades,
                "",
                HttpStatus.OK.value()
        );
    }

    //get grade by name
    @PostAuthorize("hasAuthority(\"can_view_grades\")")
    @GetMapping("/grades/{grade_name}")
    public ApiResponse getGradesPerName(@PathVariable("grade_name") String grade_name) {
        Grades grades = gradesServiceImpl.getByName(grade_name);
        return new ApiResponse(
                grades,
                "",
                HttpStatus.OK.value()
        );
    }

    //get grade by ID
    @PostAuthorize("hasAuthority(\"can_view_grades\")")
    @GetMapping("/grades/grade/{grade_id}")
    public ApiResponse getGradesById(@PathVariable("grade_id") String grade_id) {
        Grades grades = gradesServiceImpl.getById(UUID.fromString(grade_id));
        return new ApiResponse(
                grades,
                "",
                HttpStatus.OK.value()
        );
    }

    //delete grade
    @PostAuthorize("hasAuthority(\"can_delete_grades\")")
    @DeleteMapping("/grades/{grade_id}/delete")
    public ApiResponse deleteGrades(@PathVariable("grade_id") String grade_id) {
        Grades grades = gradesServiceImpl.getById(UUID.fromString(grade_id));
        gradesServiceImpl.delete(grades);
        return new ApiResponse(
                grades,
                "Grades Deleted Successfully",
                HttpStatus.OK.value()
        );
    }
}
