package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.TermDetailsDtos;
import lunna.school.exception.BadRequestException;
import lunna.school.model.TermDetails;
import lunna.school.model.Terms;
import lunna.school.model.User;
import lunna.school.service.TermDetailsService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/18/22 2:23 PM
 * school
 * TermDetailsController
 * IntelliJ IDEA
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
@RequestMapping("/terms_details")
public class TermDetailsController {
    final TermDetailsService termDetailsService;
    final UserService userService;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TermDetailsController(TermDetailsService termDetailsService, UserService userService) {
        this.termDetailsService = termDetailsService;
        this.userService = userService;
    }

    @PostAuthorize("hasAuthority(\"can_add_terms_details\")")
    @PostMapping("/create")
    public ApiResponse createSchoolTerms(@RequestBody TermDetails termDetails, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        termDetails.setCreated_by(user);
        TermDetails termDetails1;
        try {
            termDetails1 = termDetailsService.saveOrUpdate(termDetails);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                modelMapper.map(termDetails1, TermDetailsDtos.class),
                "Terms Details Created Successfully",
                HttpStatus.OK.value()

        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_terms_details\")")
    @PutMapping("/update")
    public ApiResponse updateSchoolTerms(@RequestBody TermDetails termDetails, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        termDetails.setCreated_by(user);
        TermDetails termDetails1;
        try {
            termDetails1 = termDetailsService.saveOrUpdate(termDetails);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                modelMapper.map(termDetails1, TermDetailsDtos.class),
                "Terms Details Updated Successfully",
                HttpStatus.OK.value()

        );
    }

    @PostAuthorize("hasAuthority(\"can_view_term_details\")")
    @GetMapping("/list")
    public ApiResponse getSchoolTerms(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<TermDetailsDtos> termDetailsDtos = termDetailsService.findBySchoolId(user.getOrganization().getOrg_id())
                .stream()
                .map(td -> modelMapper.map(td, TermDetailsDtos.class))
                .collect(Collectors.toList());
        return new ApiResponse(
                termDetailsDtos,
                "Terms",
                HttpStatus.OK.value()

        );
    }
}
