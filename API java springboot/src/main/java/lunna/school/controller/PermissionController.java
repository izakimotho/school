package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.Permission;
import lunna.school.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 19. Jul 2021 11:16 AM
 *
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PermissionController {
    final
    PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostAuthorize("hasAuthority(\"can_view_role_permission\")")
    @GetMapping("/permissions")
    public ApiResponse listAll(HttpServletRequest request){
        List<Permission> permissions = null;
        if(request.isUserInRole("ROLE_Super Admin")){
            permissions = permissionService.listAll();
        }else {
            permissions = permissionService.getByUserLevel(1L);
        }
        return new ApiResponse(
                permissions,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_role_permission\")")
    @GetMapping("/permission/{permission_id}")
    public ApiResponse getById(@PathVariable Long permission_id){
        return new ApiResponse(
                permissionService.getById(permission_id),
                "",
                HttpStatus.OK.value()
        );
    }
}
