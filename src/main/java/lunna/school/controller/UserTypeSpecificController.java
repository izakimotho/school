package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.Organization;
import lunna.school.model.UserTypeSpecific;
import lunna.school.service.SchoolService;
import lunna.school.service.UserService;
import lunna.school.service.impl.UserTypeSpecificServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * UserTypeSpecificController
 *
 * @author Collins K. Sang
 * 2021/07/13 16:03
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserTypeSpecificController {

    final UserTypeSpecificServiceImpl userTypeSpecificServiceImpl;
    final UserService userService;
    final SchoolService schoolService;

    @Autowired
    public UserTypeSpecificController(UserTypeSpecificServiceImpl userTypeSpecificServiceImpl, UserService userService, SchoolService schoolService) {
        this.userTypeSpecificServiceImpl = userTypeSpecificServiceImpl;
        this.userService = userService;
        this.schoolService = schoolService;
    }

    //create UserTypes Details
    @PostAuthorize("hasAuthority(\"can_add_user_type_specific\")")
    @PostMapping("/user_type_details/create")
    public ApiResponse createDetails(@RequestBody UserTypeSpecific userTypeSpecific, Principal principal) {
        String created_by = principal.getName();
        Organization organization = userService.findByUsername(created_by).getOrganization();
        userTypeSpecific.setCreated_by(created_by);
        userTypeSpecific.setOrganization(organization);
        userTypeSpecific.setCreatedAt(new Date());
        return new ApiResponse(
                userTypeSpecificServiceImpl.saveOrUpdate(userTypeSpecific),
                "User Types Created Successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_user_type_specific\")")
    @PutMapping("/user_type_details/{user_type_id}/update")
    public ApiResponse updateDetails(@RequestBody UserTypeSpecific userTypeSpecific, Principal principal, @PathVariable("user_type_id") String user_type_id) {
        String created_by = principal.getName();
        UserTypeSpecific userTypeSpecific1 = userTypeSpecificServiceImpl.getById(UUID.fromString(user_type_id));
        Organization organization = userService.findByUsername(created_by).getOrganization();

        userTypeSpecific.setUser_type_id(userTypeSpecific1.getUser_type_id());
        userTypeSpecific.setCreatedAt(userTypeSpecific1.getCreatedAt());
        userTypeSpecific.setCreated_by(created_by);
        userTypeSpecific.setOrganization(organization);
        userTypeSpecific.setUpdatedAt(new Date());

        return new ApiResponse(
                userTypeSpecificServiceImpl.saveOrUpdate(userTypeSpecific),
                "User Types Updated Successfully",
                HttpStatus.OK.value()
        );
    }


    @PostAuthorize("hasAuthority(\"can_delete_user_type_specific\")")
    @DeleteMapping("/user_type_details/{user_type_id}/delete")
    public ApiResponse deleteDetails(Principal principal, @PathVariable("user_type_id") String user_type_id) {
        var created_by = principal.getName();
        UserTypeSpecific userTypeSpecific = new UserTypeSpecific();
        UserTypeSpecific userTypeSpecific1 = userTypeSpecificServiceImpl.getById(UUID.fromString(user_type_id));
        Organization organization = userService.findByUsername(created_by).getOrganization();
        userTypeSpecific.setUser_type_id(userTypeSpecific1.getUser_type_id());
        userTypeSpecific.setUser_type(userTypeSpecific1.getUser_type());
        userTypeSpecific.setType_name(userTypeSpecific1.getType_name());
        userTypeSpecific.setCreatedAt(userTypeSpecific1.getCreatedAt());
        userTypeSpecific.setCreated_by(created_by);
        userTypeSpecific.setOrganization(organization);
        userTypeSpecific.setUpdatedAt(userTypeSpecific1.getUpdatedAt());
        userTypeSpecific.setDeletedAt(new Date());
        userTypeSpecificServiceImpl.delete(userTypeSpecific);
        return new ApiResponse(
                userTypeSpecific,
                "User Types Deleted Successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_user_type_specific\")")
    @GetMapping("/user_type_details/list")
    public ApiResponse getTypesList() {
        return new ApiResponse(
                userTypeSpecificServiceImpl.listAll(),
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_user_type_specific\")")
    @GetMapping("/user_type_details/schools/{school_id}/list")
    public ApiResponse getTypesListPerSchool(@PathVariable("school_id") String school_id) {
        Organization organization = schoolService.getSchoolById(UUID.fromString(school_id));
        return new ApiResponse(
                userTypeSpecificServiceImpl.getPerOrganization(organization),
                "",
                HttpStatus.OK.value()
        );
    }

}
