package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.SchoolDto;
import lunna.school.dto.SchoolDtoExt;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.EntityAlreadyExistsException;
import lunna.school.model.*;
import lunna.school.repository.RoleRepository;
import lunna.school.repository.UserRepository;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.*;
import lunna.school.service.impl.UserLevelServiceImpl;
import lunna.school.service.impl.UserServiceImpl;
import lunna.school.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:34 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class SchoolController {
    private final SchoolService schoolService;
    ModelMapper modelMapper = new ModelMapper();
    final PasswordEncoder encoder;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final StaffService staffService;
    final UserLevelServiceImpl userCategoryServiceImpl;

    final UserService userService;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    JwtUtils utils;
    @Autowired
    public EmailService emailService;

    @Autowired
    public SchoolController(SchoolService schoolService, PasswordEncoder encoder, UserRepository userRepository,
                            RoleRepository roleRepository, StaffService staffService,
                            UserLevelServiceImpl userCategoryServiceImpl, UserServiceImpl userService) {
        this.schoolService = schoolService;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffService = staffService;
        this.userCategoryServiceImpl = userCategoryServiceImpl;
        this.userService = userService;
    }

    @PostAuthorize("hasAuthority(\"can_view_organization\")")
    @GetMapping("/schools/list")
    public ApiResponse getAllSchools(HttpServletRequest request) {
        List<Organization> schoolList = new ArrayList<>();
        if (request.isUserInRole("ROLE_Super Admin")) {
            schoolList = schoolService.listSchools();
        } else {
            schoolList = schoolService.listSchools(utils.getSchoolId(request));
        }

        List<SchoolDtoExt> schoolDtoList = schoolList
                .stream()
                .map(s ->
                        modelMapper.map(s, SchoolDtoExt.class)

                )
                .collect(Collectors.toList());
        return new ApiResponse(
                schoolDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_organization\")")
    @PostMapping("/schools/create")
    public ApiResponse addSchool(@Valid @RequestBody SchoolDtoExt schoolDto, Principal principal,
                                 BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }

        String created_by = principal.getName();

        if (userRepository.existsByUsername(schoolDto.getEmail_address())) {

            throw new EntityAlreadyExistsException("Error: Email is already taken!");
        }

        if (userRepository.existsByEmail(schoolDto.getEmail_address())) {
            throw new EntityAlreadyExistsException("Error: Email is already in use!");
        }

        Organization organization = schoolService.saveSchool(schoolDto);

        SchoolDto schoolDto1 = modelMapper.map(organization, SchoolDto.class);

        UserLevel userLevel = userCategoryServiceImpl.getByName("School Admin");

        //create school admin
        String password = Util.generatePassword(8);
        User user = new User();
        user.setUsername(schoolDto.getEmail_address());
        user.setEmail(schoolDto.getEmail_address());
        user.setFirstName(schoolDto.getOrg_name());
        user.setLastName(schoolDto.getOrg_name());
        user.setPassword(encoder.encode(password));
        user.setOrganization(organization);
        user.setActive(true);
        user.setAvatar(organization.getLogo());

        user.setUser_category(userLevel);
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRole_name("Administration");
        roles.add(role);
        user.setRoles(roles);
        User user1 = userService.saveUser(user);
        try {


            UserType userType = userTypeService.getById(1L);

            Staff staff = new Staff(
                    userType,
                    schoolDto1.getOrg_name(),
                    schoolDto1.getOrg_name(),
                    schoolDto1.getOrg_name(),
                    schoolDto1.getEmail_address(),
                    schoolDto1.getPhone_numbers() == null || schoolDto1.getPhone_numbers().isEmpty() ? null : schoolDto1.getPhone_numbers().get(0),
                    user1,
                    organization,
                    null,
                    created_by
            );
            Staff staff1;

            staff1 = staffService.save(staff);
            System.out.println(staff1.getEmail() + " Has been created successfully");
        } catch (Exception e) {

            userRepository.delete(user1);
            schoolService.delete(organization);
            throw new BadRequestException("Error: " + e.getCause().getMessage());
        }

        Mail mail = new Mail();
        mail.setSubject("Login Password");
        mail.setRecipientName(user1.getFirstName());
        mail.setSenderName("Lunna");
        mail.setText("Password: "+password);
        mail.setTo(user1.getEmail());
        emailService.sendMessage(mail.getTo(),
                mail.getSubject(), mail.getText());

        return new ApiResponse(
                schoolDto1,
                "School added successfully",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_edit_organization\")")
    @PutMapping("/schools/update")
    public ApiResponse updateSchool(@RequestBody SchoolDtoExt schoolDto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        SchoolDto schoolDto1 = modelMapper.map(schoolService.updateSchool(schoolDto), SchoolDto.class);

        return new ApiResponse(
                schoolDto1,
                "School updated successfully",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_view_organization\")")
    @GetMapping("/schools/school/{school_id}")
    public ApiResponse getSchoolById(@PathVariable("school_id") String school_id) {
        Organization organization = schoolService.getSchoolById(UUID.fromString(school_id));
        return new ApiResponse(
                organization,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_organization\")")
    @DeleteMapping("/schools/delete/{id}")
    public ApiResponse deleteSchool(@PathVariable("id") String id) {
        Organization school = schoolService.getSchoolById(UUID.fromString(id));
        schoolService.softDelete(school);
        return new ApiResponse(
                null,
                "School deleted",
                HttpStatus.OK.value()
        );
    }


}
