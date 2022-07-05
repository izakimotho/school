package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.PositionDto;
import lunna.school.dto.SchoolDto;
import lunna.school.dto.StaffInfoDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.EntityAlreadyExistsException;
import lunna.school.model.*;
import lunna.school.repository.RoleRepository;
import lunna.school.repository.UserLevelRepository;
import lunna.school.repository.UserRepository;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.*;
import lunna.school.service.impl.PositionServiceImpl;
import lunna.school.service.impl.UserServiceImpl;
import lunna.school.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * StaffController
 *
 * @author Collins K. Sang
 * 2021/06/30 09:11
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StaffController {

    final PasswordEncoder encoder;
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final StaffService staffService;
    final SchoolService schoolService;
    final PositionServiceImpl positionServiceImpl;
    final UserLevelService userLevelService;
    @Autowired
    public EmailService emailService;
    final UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtUtils utils;
    @Autowired
    public StaffController(PasswordEncoder encoder, RoleRepository roleRepository,
                           UserRepository userRepository, StaffService staffService, SchoolService schoolService,
                           PositionServiceImpl positionServiceImpl,
                           UserLevelService userLevelService, UserServiceImpl userService) {
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.staffService = staffService;
        this.schoolService = schoolService;
        this.positionServiceImpl = positionServiceImpl;
        this.userLevelService = userLevelService;
        this.userService = userService;
    }

    ModelMapper modelMapper = new ModelMapper();

    //create staff
    @PostAuthorize("hasAuthority(\"can_add_staff\")")
    @PostMapping("/staff/create")
    public ApiResponse createStaff(@RequestBody StaffInfoDto staffInfoDto,
                                   Principal principal,
                                   HttpServletRequest request) {
        Organization school = schoolService.getSchoolById(utils.getSchoolId(request));
        UserLevel userLevel = userLevelService.getById(2);



        String created_by = principal.getName();
        SchoolDto organization = staffInfoDto.getOrganization();
        if(organization != null){
            school = modelMapper.map(organization, Organization.class);
        }
        User user = new User();
        String password = Util.generatePassword(8);
        user.setUsername(staffInfoDto.getEmail());
        user.setEmail(staffInfoDto.getEmail());
        user.setFirstName(staffInfoDto.getFirst_name());
        user.setLastName(staffInfoDto.getSurname());
        user.setAvatar(staffInfoDto.getPhoto_url());
        user.setPassword(encoder.encode(password));
        user.setOrganization(school);
        user.setActive(true);

        if (userLevel != null) {
            user.setUser_category(userLevel);
        }
        Positions positions = null;

        if (staffInfoDto.getPositions() != null) {
            Staff isAvailable = staffService.getStaffByPositionAndSchool(staffInfoDto.getPositions().getPosition_id(), school.getOrg_id());
            if (isAvailable != null) {
                throw new BadRequestException("Error: Position already taken, try a different position");
            }
            boolean isPresent = staffInfoDto.getPositions().getPosition_id() != null;
            positions = isPresent ?
                    positionServiceImpl.getById(staffInfoDto.getPositions().getPosition_id()) :
                    null;
            staffInfoDto.setPositions(modelMapper.map(positions, PositionDto.class));
        }

        Set<Role> strRoles = staffInfoDto.getRole();

        Set<Role> roles = new HashSet<>();
        Role userRole = null;
        if (strRoles == null) {
            userRole = roleService.getByNameOrgId("User",school.getOrg_id());
            roles.add(userRole);
        } else {
            roles = strRoles;

        }

        if (userService.userExist(staffInfoDto.getEmail())) {

            throw new EntityAlreadyExistsException("Error: Username is already taken!");

        }

        if (userRepository.existsByEmail(staffInfoDto.getEmail())) {
            throw new EntityAlreadyExistsException("Error: Email is already in use!");


        }
        user.setRoles(roles);
        User user1 = userService.saveUser(user);

        Staff staff = new Staff(
                staffInfoDto.getType(),
                staffInfoDto.getFirst_name(),
                staffInfoDto.getMiddle_name(),
                staffInfoDto.getSurname(),
                staffInfoDto.getEmail(),
                staffInfoDto.getPassword(),
                staffInfoDto.getGender(),
                staffInfoDto.getTitle(),
                staffInfoDto.getPhone_number(),
                staffInfoDto.getMarital_status(),
                staffInfoDto.getSpouse_name(),
                staffInfoDto.getSpouse_phone(),
                staffInfoDto.getPhoto_url(),
                staffInfoDto.getEmployment_date(),
                user1,
                school,
                positions,
                created_by
        );
        Staff staff1;
        try {
            staff1 = staffService.save(staff);
        } catch (DataIntegrityViolationException e) {
            userRepository.delete(user1);
            throw new BadRequestException("Error: " + e.getMostSpecificCause().getMessage());
        }
        StaffInfoDto staffInfoDto1 = modelMapper.map(staff1, StaffInfoDto.class);
        Mail mail = new Mail();
        mail.setSubject("Login Password");
        mail.setRecipientName(user1.getFirstName());
        mail.setSenderName("Lunna");
        mail.setText(password);
        mail.setTo(user1.getEmail());
        emailService.sendMessage(mail.getTo(),
                mail.getSubject(), mail.getText());
        return new ApiResponse(
                staffInfoDto1,
                "Success: Staff Created Successfully!",
                HttpStatus.OK.value());


    }

    static User userObject(User user, UserRepository userRepository) {
        User user1;
        try {
            user1 = userRepository.save(user);
            return user1;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Error: " + e.getCause().getMessage());
        }
    }

    //update staff
    @PostAuthorize("hasAuthority(\"can_edit_staff\")")
    @PutMapping("/staff/update")
    public ApiResponse updateStaff(@RequestBody Staff staff, Principal principal) {
        String created_by = principal.getName();
        Organization organization = schoolService.getSchoolById(staff.getOrganization().getOrg_id());

        if (staff.getPositions() != null) {
            Positions position = null;
            try{
                position = positionServiceImpl.getById(staff.getPositions().getPosition_id());
                System.out.println(position.getPosition_id());
            }catch (EntityNotFoundException e){
                throw new BadRequestException(e.getLocalizedMessage());
            }

            staff.setPositions(position);
        }

        staff.setOrganization(organization);

        Staff staff1 = staffService.getStaffById(staff.getStaff_id());

        Staff staffs = new Staff(
                staff1.getStaff_id(),
                staff.getType(),
                staff.getFirst_name(),
                staff.getMiddle_name(),
                staff.getSurname(),
                staff.getEmail(),
                staff.getPassword(),
                staff.getGender(),
                staff.getTitle(),
                staff.getPhone_number(),
                staff.getMarital_status(),
                staff.getSpouse_name(),
                staff.getSpouse_phone(),
                staff.getAvatar(),
                staff.getEmployment_date(),
                created_by,
                staff1.getUser(),
                staff.getOrganization(),
                staff.getPositions()
        );
        StaffInfoDto staffInfoDto1 = null;
        User user1 = modelMapper.map(staffs, User.class); //;
        userService.updateUser(user1);

        try{
            staffInfoDto1 = modelMapper.map(staffService.save(staffs), StaffInfoDto.class);
        }catch (Exception e){
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                staffInfoDto1,
                "Success: Staff Updated Successfully!",
                HttpStatus.OK.value());

    }

    //list all staff
    @PostAuthorize("hasAuthority(\"can_view_staff\")")
    @GetMapping("/staff/list")
    public ApiResponse listStaff(HttpServletRequest request) {
        List<Staff> staffList = new ArrayList<>();
        if (request.isUserInRole("ROLE_Super Admin")) {
            staffList = staffService.getAllStaff();
            System.out.println("admin"+utils.getSchoolId(request));
        }else {
            staffList = staffService.getStaffPerSchool(utils.getSchoolId(request));
        }

        List<StaffInfoDto> staffInfoDtoList = staffList
                .stream().map(
                        s -> new StaffInfoDto(
                                s.getStaff_id(),
                                s.getType(),
                                s.getFirst_name(),
                                s.getMiddle_name(),
                                s.getSurname(),
                                s.getEmail(),
                                s.getGender(),
                                s.getTitle(),
                                s.getPhone_number(),
                                s.getMarital_status(),
                                s.getSpouse_name(),
                                s.getSpouse_phone(),
                                s.getAvatar(),
                                s.getEmployment_date(),
                                modelMapper.map(s.getOrganization(), SchoolDto.class),
                                s.getPositions()!= null ? modelMapper.map(s.getPositions(), PositionDto.class): null
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                staffInfoDtoList,
                "Subject list",
                HttpStatus.OK.value()
        );

    }

    //get specific staff
    @PostAuthorize("hasAuthority(\"can_view_staff\")")
    @GetMapping("/staff/{id}")
    public ApiResponse getStaff(@PathVariable("id") String id) {
        Staff staff = staffService.getStaffById(UUID.fromString(id));
        StaffInfoDto staffInfoDto = modelMapper.map(staff, StaffInfoDto.class);
        return new ApiResponse(
                staffInfoDto,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_staff\")")
    @DeleteMapping("/staff/{id}")
    public ApiResponse delete(@PathVariable("id") String id) {
        Staff staff = staffService.getStaffById(UUID.fromString(id));
        staffService.delete(staff);
        return new ApiResponse(
                null,
                "",
                HttpStatus.OK.value()
        );
    }
    //get specific staff by email
    @PostAuthorize("hasAuthority(\"can_view_staff\")")
    @GetMapping("/staff/{email}/email")
    public ApiResponse getStaffs(@PathVariable("email") String email) {
        Staff staff = staffService.getStaffByEmail(email);
        StaffInfoDto staffInfoDto = modelMapper.map(staff, StaffInfoDto.class);
        return new ApiResponse(
                staffInfoDto,
                "",
                HttpStatus.OK.value()
        );
    }

    //get staff per school
    @PostAuthorize("hasAuthority(\"can_view_staff\")")
    @GetMapping("/schools/{school_id}/staff")
    public ApiResponse getStaffPerSchool(@PathVariable String school_id) {
        SchoolDto organization = modelMapper.map(schoolService.getSchoolById(UUID.fromString(school_id)), SchoolDto.class);

        List<StaffInfoDto> staffInfoDtoList = staffService.getStaffPerSchool(UUID.fromString(school_id)).stream().map(
                        s -> new StaffInfoDto(
                                s.getStaff_id(),
                                s.getType(),
                                s.getFirst_name(),
                                s.getMiddle_name(),
                                s.getSurname(),
                                s.getEmail(),
                                s.getGender(),
                                s.getTitle(),
                                s.getPhone_number(),
                                s.getMarital_status(),
                                s.getSpouse_name(),
                                s.getSpouse_phone(),
                                s.getAvatar(),
                                s.getEmployment_date(),
                                organization,
                                modelMapper.map(s.getPositions(), PositionDto.class)
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                staffInfoDtoList,
                "Staff list",
                HttpStatus.OK.value()
        );
    }

    //get staff per gender
    @PostAuthorize("hasAuthority(\"can_view_staff\")")
    @GetMapping("/staff/gender/{gender}")
    public ApiResponse getStaffPerGender(@PathVariable GenderEnum gender) throws ConversionFailedException {
        System.out.println(gender + "\n" + GenderEnum.MALE);

        List<StaffInfoDto> staffInfoDtoList = staffService.getStaffPerGender(gender).stream().map(
                        s -> new StaffInfoDto(
                                s.getStaff_id(),
                                s.getType(),
                                s.getFirst_name(),
                                s.getMiddle_name(),
                                s.getSurname(),
                                s.getEmail(),
                                s.getGender(),
                                s.getTitle(),
                                s.getPhone_number(),
                                s.getMarital_status(),
                                s.getSpouse_name(),
                                s.getSpouse_phone(),
                                s.getAvatar(),
                                s.getEmployment_date(),
                                modelMapper.map(s.getOrganization(), SchoolDto.class),
                                modelMapper.map(s.getPositions(), PositionDto.class)
                        ))
                .collect(Collectors.toList());
        return new ApiResponse(
                staffInfoDtoList,
                "Staff by gender",
                HttpStatus.OK.value()
        );
    }

}
