package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.RoleDto;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Organization;
import lunna.school.model.Role;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 6:21 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RoleController {
    private final RoleService roleService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    JwtUtils utils;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //create roles
    @PostAuthorize("hasAuthority(\"can_add_roles\")")
    @PostMapping("/roles/create")
    public ApiResponse create(@Valid @RequestBody Role role, HttpServletRequest request) {
        Organization school =  utils.getSchool(request);
        if (role.getOrganization() == null){
            role.setOrganization(school);
        }
        RoleDto roleDto = null;
        try {
            roleDto = modelMapper.map(roleService.saveOrUpdate(role), RoleDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                roleDto,
                "Role Created Successfully",
                HttpStatus.OK.value()
        );
    }

    //edit roles
    @PostAuthorize("hasAuthority(\"can_edit_roles\")")
    @PutMapping("roles/update")
    public ApiResponse updateRoles(@Valid @RequestBody Role role) {
        RoleDto roleDto = null;
        Role role1 = roleService.getById(role.getRole_id());
        role1.setRole_name(role.getRole_name());
        role1.setDescription(role.getDescription());
        role1.setPermissions(role.getPermissions());
        try {
            roleDto = modelMapper.map(roleService.saveOrUpdate(role1), RoleDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                roleDto,
                "Roles Updated Successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_roles\")")
    @GetMapping("/roles")
    public ApiResponse getRoles(HttpServletRequest request) {
        List<RoleDto> dtoList = roleService.getByOrgId(utils.getSchoolId(request)).
                stream().map(
                        role -> modelMapper.map(role, RoleDto.class)
                ).collect(Collectors.toList());

        return new ApiResponse(
                dtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_roles\")")
    @GetMapping("/roles/{role_name}")
    public ApiResponse getRoleByName(@PathVariable("role_name") String role_name) {
        Role role1 = roleService.getByName(role_name);
        return new ApiResponse(
                role1,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_roles\")")
    @GetMapping("/role/{role_id}")
    public ApiResponse getRoleById(@PathVariable("role_id") String role_id) {
        Role role1 = roleService.getById(UUID.fromString(role_id));
        return new ApiResponse(
                role1,
                "",
                HttpStatus.OK.value()
        );
    }

}
