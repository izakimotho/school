package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.TermsDto;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Grades;
import lunna.school.model.Terms;
import lunna.school.model.User;
import lunna.school.service.TermsService;
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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jul 2021 11:55 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
@RequestMapping("/terms")
public class TermsController {
    final TermsService termsService;
    final UserService userService;
ModelMapper modelMapper = new ModelMapper();
    @Autowired
    public TermsController(TermsService termsService, UserService userService) {
        this.termsService = termsService;
        this.userService = userService;
    }

    @PostAuthorize("hasAuthority(\"can_add_terms\")")
    @PostMapping("/create")
    public ApiResponse createSchoolTerms(@RequestBody Terms terms, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        terms.setCreated_by(user);
        terms.setOrganization(user.getOrganization());
        Terms terms1;
        try {
            terms1 = termsService.saveOrUpdate(terms);
    } catch (
    DataIntegrityViolationException e) {
        throw new BadRequestException(e.getMostSpecificCause().getMessage());
    }
        return new ApiResponse(
                modelMapper.map(terms1, TermsDto.class),
                "Terms Created Successfully",
                HttpStatus.OK.value()

        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_terms\")")
    @PutMapping("/update")
    public ApiResponse updateSchoolTerms(@RequestBody Terms terms, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        terms.setCreated_by(user);
        terms.setOrganization(user.getOrganization());
        Terms terms1;
        try {
            terms1 = termsService.saveOrUpdate(terms);
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                modelMapper.map(terms1, TermsDto.class),
                "Terms Updated Successfully",
                HttpStatus.OK.value()

        );
    }

    @PostAuthorize("hasAuthority(\"can_view_terms\")")
    @GetMapping("/list")
    public ApiResponse getSchoolTerms(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<Terms> terms1;
        List<TermsDto> termsDtos;
        try {
            termsDtos = termsService.findBySchoolId(user.getOrganization().getOrg_id()).stream()
                    .map(t -> modelMapper.map(t, TermsDto.class))
                    .collect(Collectors.toList());
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                termsDtos,
                "Terms",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_terms\")")
    @DeleteMapping("/{term_id}/delete")
    public ApiResponse deleteGrades(@PathVariable("term_id") String term_id) {
        Terms terms = termsService.getById(UUID.fromString(term_id));
        try {
            termsService.delete(terms);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                terms,
                "Terms Deleted Successfully",
                HttpStatus.OK.value()
        );
    }

}
