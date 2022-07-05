package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.UserDto;
import lunna.school.dto.UserDtoExt;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.Role;
import lunna.school.model.User;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.RoleService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 10:19 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    JwtUtils utils;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService,  RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test controller";
    }

    @PostMapping("/roles/assign")
    public ApiResponse assignRole(@RequestBody UserDtoExt userdto) {
        if (userdto == null) {
            throw new BadRequestException("User details missing");
        }
        User user = userService.findByUsername(userdto.getUsername());
        Set<Role> roles = new HashSet<>();
        if (user != null) {
            for (Role role : userdto.getRoles()
            ) {
                roles.add(roleService.getById(role.getRole_id()));
            }

        }
        List<Role> toInsert = roles.stream().filter(e -> !user.getRoles().contains(e)).collect(Collectors.toList());

        user.getRoles().addAll(new HashSet(toInsert));

        User user1 = null;
        try {
            user1 = userService.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                modelMapper.map(user1, UserDto.class),
                "",
                HttpStatus.OK.value()
        );
    }

    @PostMapping("/{user_email}/activate")
    public ApiResponse activateDeactivateUser(@PathVariable("user_email") String user_email) {
        User user = userService.findUserByEmail(user_email);
        user.setActive(!user.isActive());
        return new ApiResponse(
                userService.saveUser(user),
                "User isActive status changed successfully",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_view_user\")")
    @GetMapping("/list")
    public ApiResponse getAll() {
        return new ApiResponse(
                userService.getAllUsers(),
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/profile")
    public ApiResponse getUserProfile(HttpServletRequest request) {
        String token = utils.getToken(request);
        User user = userService.findByUsername(utils.getUserNameFromJwtToken(token));
        UserDtoExt userDtoExt = modelMapper.map(user, UserDtoExt.class);
        return new ApiResponse(
                userDtoExt,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_user\")")
    @GetMapping("/user_types")
    public ApiResponse getUserTypes() {
        return new ApiResponse(
                userService.getUserTypes(),
                "",
                HttpStatus.OK.value()
        );
    }


    @PostAuthorize("hasAuthority(\"can_delete_user\")")
    @DeleteMapping("/delete/{username}")
    public ApiResponse deleteUser(@PathVariable("username") String username) {
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Requested variable should not be null");
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RecordNotFoundException("User " + username + " Not Found");

        } else {
            try {
                userService.deleteUser(user);
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("User data constraint violation, couldn't delete");
            }

        }

        return new ApiResponse(
                null,
                "User Deleted ",
                HttpStatus.OK.value()
        );
    }
}
