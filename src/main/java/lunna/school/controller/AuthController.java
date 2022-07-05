package lunna.school.controller;

import lunna.school.dto.*;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.ExpectationFailedException;
import lunna.school.exception.NoContentException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.repository.RoleRepository;
import lunna.school.repository.UserLevelRepository;
import lunna.school.repository.UserRepository;
import lunna.school.security.CustomUserDetails;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.EmailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 26. Jun 2021 6:22 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    ModelMapper modelMapper = new ModelMapper();
    final
    AuthenticationManager authenticationManager;

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;


    final
    UserLevelRepository userLevelRepository;

    final
    PasswordEncoder encoder;

    final
    JwtUtils jwtUtils;

    @Autowired
    public EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository, RoleRepository roleRepository,
                          UserLevelRepository userLevelRepository,
                          PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userLevelRepository = userLevelRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ApiResponse authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        if (loginDto.getUsername() == null || loginDto.getUsername().isEmpty()) {
            logger.error("Username  is null");
            throw new BadRequestException("Username  is required");
        }
        if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            logger.error("account pin is required");
            throw new BadRequestException("Password is required");

        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            logger.info("error =>=>=>\n" + e.getLocalizedMessage());
            throw new BadCredentialsException("Wrong Username or Password", e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return new ApiResponse(
                new JwtResponse(jwt, userDetails.getUsername(),roles),
                "",
                HttpStatus.OK.value()
        );
    }

    @PostMapping("/register")
    public ApiResponse registerUser(@Valid @RequestBody RegisterDto registerDto, BindingResult result) {
        logger.info("" + result.getErrorCount());
        for (ObjectError er : result.getAllErrors()) {
            logger.info(er.getDefaultMessage());
        }
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BadRequestException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(registerDto.getEmail());
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(encoder.encode(registerDto.getPassword()));
        user.setActive(true);
        user.setOrganization(registerDto.getOrganization());

        Set<UUID> strRoles = registerDto.getRole();

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole_name("User");
            roles.add(userRole);
        } else {
            roles = new HashSet<>(roleRepository.findByRole_idIn(strRoles));

        }

        if (registerDto.getUser_level() != null) {
            UserLevel userLevel = userLevelRepository.findById(registerDto.getUser_level().getUser_level()).orElse(null);
            user.setUser_category(userLevel);
        }
        user.setRoles(roles);
        logger.info("info" + roles);
        User user1;
        try {
            user1 = userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            logger.info(ex.getMessage());
            throw new RecordNotFoundException(ex.getMessage());
        }
        Mail mail = new Mail();
        mail.setSubject("Login Password");
        mail.setRecipientName(user1.getFirstName());
        mail.setSenderName("Lunna");
        mail.setText(registerDto.getPassword());
        mail.setTo(user1.getEmail());
        emailService.sendMessage(mail.getTo(),
                mail.getSubject(), mail.getText());

        return new ApiResponse(
                modelMapper.map(user1, UserDto.class),
                "User registered successfully!",
                HttpStatus.OK.value()
        );
    }


    //change password

    @PostMapping("/change_pass")
    public ApiResponse changePass(Principal principal, @RequestBody ChangePassword changePassword, HttpServletRequest request) throws ServletException {
        if (principal == null) {
            throw new BadRequestException("Token not found/expired");
        }

        if (changePassword.getOld_password() == null|| changePassword.getOld_password().isEmpty()) {
            throw new BadRequestException("Old Password is required (old_password=blank)");
        }

        if (changePassword.getNew_password() == null|| changePassword.getNew_password().isEmpty()) {
            throw new BadRequestException("New Password is required (new_password=blank)");
        }
        User user = userRepository.findByUsername(principal.getName());

        String jwt = passJwt(request);

        System.out.println(request.getHeader("Authorization"));
        request.logout();

        String old_pass_db = user.getPassword();
        String old_pass_passed = changePassword.getOld_password();
        String new_pass = changePassword.getNew_password();
        String confirm_password = changePassword.getConfirm_password();

        //encode old_passed password and compare with existing password.


//        String encoded_old_pass = encoder.encode(old_pass_passed).matches(old_pass_db);
        if (!encoder.matches(old_pass_passed, old_pass_db)) {
            throw new ExpectationFailedException("Old password doesn't match the old password provided!");
        }

        //encode new pass
        String encoded_password = encoder.encode(new_pass);
        if (old_pass_passed.equals(new_pass)) {
            throw new ExpectationFailedException("Old password should not be same as the new password!");
        }
        //check if new pass is same as confirm pass
        if (!new_pass.equals(confirm_password)) {
            throw new ExpectationFailedException("Confirm password does not match with the password provided!");
        }

        //if the above checks are successful proceed to changing password

        user.setPassword(encoded_password);
        User user1 = userRepository.save(user);

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user1.getUsername(), changePassword.getNew_password()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        //expires old token
        jwtUtils.updateExpiryForJwtToken(jwt).setExpiration(new Date()).clear();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt1 = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new ApiResponse(
                new JwtResponse(jwt,
                        userDetails.getUsername(),
                        roles),
                "Password changed successfully!",
                HttpStatus.OK.value()
        );
    }

    private String passJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    @PostMapping("/forgot_password")
    public ApiResponse forgot_pass(@Valid @RequestBody ForgetPassword forgetPassword) {

        if (forgetPassword.getEmail() == null || forgetPassword.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required (email=blank)");

        }
        //check if new pass is same as confirm pass
        if (!forgetPassword.getNew_password().equals(forgetPassword.getConfirm_password())) {
            throw new ExpectationFailedException("Confirm password does not match with the password provided!");
        }

        String encoded_pass = encoder.encode(forgetPassword.getNew_password());
        User user = userRepository.findByEmail(forgetPassword.getEmail());
        if (user == null) {
            throw new NoContentException("User Does not exist");
        } else {
            user.setPassword(encoded_pass);
            userRepository.save(user);
            return new ApiResponse(
                    null,
                    "Password changed successfully. Proceed to login",
                    HttpStatus.OK.value()
            );

        }
    }
}
