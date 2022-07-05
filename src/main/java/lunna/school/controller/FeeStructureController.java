package lunna.school.controller;

import lunna.school.dto.*;
import lunna.school.exception.BadRequestException;
import lunna.school.model.AcademicYear;
import lunna.school.model.FeeStructure;
import lunna.school.model.Terms;
import lunna.school.model.User;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.AcademicYearService;
import lunna.school.service.FeeStructureService;
import lunna.school.service.TermsService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/19/22 9:57 AM
 * school
 * FeeStructureController
 * IntelliJ IDEA
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FeeStructureController {
    final FeeStructureService feeStructureService;
    final UserService userService;
    final AcademicYearService academicYearService;
    ModelMapper modelMapper = new ModelMapper();
    final TermsService termsService;
    @Autowired
    JwtUtils utils;

    @Autowired
    public FeeStructureController(FeeStructureService feeStructureService, UserService userService, AcademicYearService academicYearService, TermsService termsService) {
        this.feeStructureService = feeStructureService;
        this.userService = userService;
        this.academicYearService = academicYearService;
        this.termsService = termsService;
    }

    @PostAuthorize("hasAuthority(\"can_add_fee_structure\")")
    @PostMapping("/fees-structure")
    public ApiResponse createFeeStructure(
            @RequestBody FeeStructureReq feeStructure,
            Principal principal) {

        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);

        FeeStructureDto feeStructure1;
        try {
            feeStructure1 = feeStructureService.saveFeeStructure(feeStructure, user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructure1,
                "Fee Structure saved successfully!",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_edit_fee_structure\")")
    @PutMapping("/fees-structure")
    public ApiResponse updateFeeStructure(@RequestBody FeeStructure feeStructure, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        feeStructure.setCreated_by(user);
        FeeStructure feeStructure1;
        try {
            feeStructure1 = feeStructureService.saveOrUpdate(feeStructure);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructure1, "Fee Structure updated successfully!", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure")
    public ApiResponse getStructureDetails(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeStructureDetailsDtos> feeStructureList;
        List<Terms> terms;
        List<TermsDto> termsDtos;
        try {

            AcademicYear academicYear = academicYearService.getActiveAcademicYearBySchool(user.getOrganization().getOrg_id());
            terms = termsService.findBySchoolId(user.getOrganization().getOrg_id());
            termsDtos = terms
                    .stream().map(
                            ts -> new TermsDto(
                                    ts.getTerm_id().toString(),
                                    ts.getName(),
                                    ts.getDescription(),
                                    modelMapper.map(ts.getOrganization(), SchoolDto.class)
                            )).collect(Collectors.toList());

            feeStructureList = feeStructureService.findSumFeePerSchool(academicYear.getAcademic_year_id(), user.getOrganization().getOrg_id())

                    .stream()
                    .map(fs -> new FeeStructureDetailsDtos(
                            fs.getFee_structure_id(),
                            modelMapper.map(fs.getAcademicYear(), AcademicYearDto.class),
                            modelMapper.map(fs.getClassModel(), ClassModelDto.class),
                            termsDtos,
                            fs.getTotalAmount()
                    ))
                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructureList, "School Fee Structure", HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure/{id}")
    public ApiResponse findFeeStructurePerId(
            @PathVariable("id") String id,
            HttpServletRequest request) {

        FeeStructureDTO feeStructureDTOS;
        try {
            feeStructureDTOS = feeStructureService.getByFeeStructureId(UUID.fromString(id));
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
        return new ApiResponse(feeStructureDTOS, "Class Fee Structure List", HttpStatus.OK.value());
    }
    //    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure/year/{academic_year}")
    public ApiResponse getFeeStructure(@PathVariable("academic_year") String academic_year, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeStructureDto> feeStructureDtoList;
        try {
            feeStructureDtoList = null;
//            feeStructureDtoList = feeStructureService.findBySchoolId(UUID.fromString(academic_year), user.getOrganization().getOrg_id()).stream()
//                    .map(fs -> new FeeStructureDto(
//                            String.valueOf(fs.getFee_structure_id()),
//                            fs.getAmount(),
//                            modelMapper.map(fs.getFee_vote(), FeeVoteHeadDto.class),
//                            modelMapper.map(fs.getClass_model(), ClassModelDto.class),
//                            modelMapper.map(fs.getAcademic_year(), AcademicYearDto.class),
//                            modelMapper.map(fs.getTerm_details(), TermDetailsDtos.class)
//                    ))
//                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructureDtoList, "School Fee Structure", HttpStatus.OK.value()
        );
    }

    //    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure/year/{academic_year}/term/{term}")
    public ApiResponse getFeeStructurePerTerm(@PathVariable("academic_year") String academic_year, @PathVariable("term") String term, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeStructureDto> feeStructureDtoList;
        try {
            feeStructureDtoList = null;
//            feeStructureDtoList = feeStructureService.findBySchoolIdAndTerm(UUID.fromString(academic_year),
//                            UUID.fromString(term),
//                            user.getOrganization().getOrg_id())
//                    .stream()
//                    .map(fs -> new FeeStructureDto(
//                            String.valueOf(fs.getFee_structure_id()),
//                            fs.getAmount(),
//                            modelMapper.map(fs.getFee_vote(), FeeVoteHeadDto.class),
//                            modelMapper.map(fs.getClass_model(), ClassModelDto.class),
//                            modelMapper.map(fs.getAcademic_year(), AcademicYearDto.class)
//                    ))
//                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructureDtoList, "School Fee Structure", HttpStatus.OK.value()
        );
    }

    //    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure/year/{academic_year}/class/{class_id}")
    public ApiResponse getFeeStructurePerYearPerClass(@PathVariable("academic_year") String academic_year, @PathVariable("class_id") String class_id, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeStructureDto> feeStructureDtoList;
        try {
            feeStructureDtoList = null;
//            feeStructureDtoList = feeStructureService
//                    .findBySchoolIdAndClassAndYear(
//                            UUID.fromString(academic_year),
//                            user.getOrganization().getOrg_id(),
//                            UUID.fromString(class_id)
//                    )
//                    .stream()
//                    .map(fs -> new FeeStructureDto(
//                            String.valueOf(fs.getFee_structure_id()),
//                            fs.getAmount(),
//                            modelMapper.map(fs.getFee_vote(), FeeVoteHeadDto.class),
//                            modelMapper.map(fs.getClass_model(), ClassModelDto.class),
//                            modelMapper.map(fs.getAcademic_year(), AcademicYearDto.class)
//                    ))
//                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructureDtoList, "School Fee Structure", HttpStatus.OK.value()
        );
    }

    //    @PostAuthorize("hasAuthority(\"can_view_fee_structure\")")
    @GetMapping("/fees-structure/year/{academic_year}/class/{class_id}/term/{term}")
    public ApiResponse getFeeStructurePerTermPerClass(@PathVariable("academic_year") String academic_year,
                                                      @PathVariable("class_id") String class_id,
                                                      @PathVariable("term") String term,
                                                      Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeStructureDto> feeStructureDtoList;
        try {
            feeStructureDtoList = null;
//            feeStructureDtoList = feeStructureService
//                    .findBySchoolIdAndClassAndTerm(
//                            UUID.fromString(academic_year),
//                            UUID.fromString(term),
//                            user.getOrganization().getOrg_id(),
//                            UUID.fromString(class_id)
//                    )
//                    .stream()
//                    .map(fs -> new FeeStructureDto(
//                            String.valueOf(fs.getFee_structure_id()),
//                            fs.getAmount(),
//                            modelMapper.map(fs.getFee_vote(), FeeVoteHeadDto.class),
//                            modelMapper.map(fs.getClass_model(), ClassModelDto.class),
//                            modelMapper.map(fs.getAcademic_year(), AcademicYearDto.class),
//                            modelMapper.map(fs.getTerm_details(), TermDetailsDtos.class)
//                    ))
//                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeStructureDtoList, "School Fee Structure", HttpStatus.OK.value()
        );
    }

//    @GetMapping("/fees-structure/class/{class_id}")
//    public ApiResponse getFeeStructurePerClass(@PathVariable("class_id") String class_id, Principal principal) {
//        String created_by = principal.getName();
//        User user = userService.findByUsername(created_by);
//        List<FeeStructureDtos> feeStructureDtoList;
//        try {
//            feeStructureDtoList = feeStructureService
//                    .findBySchoolIdAndClass(
//                            user.getOrganization().getOrg_id(),
//                            UUID.fromString(class_id)
//                    )
//                    .stream()
//                    .map(fs -> new FeeStructureDtos(
//                            UUID.fromString(String.valueOf(fs.getFee_structure_id())),
//                            fs.getFee_total_amount(),
//                            fs.getTerm_fee() != null ? fs.getTerm_fee().stream().map(
//                                            tf -> new TermFeeDto(
//                                                    tf.getTerm(),
//                                                    tf.getTerm_total_amount()
//                                            ))
//                                    .collect(Collectors.toList()) : null,
//
//                            modelMapper.map(fs.getClass_model(), ClassModelDto.class),
//                            modelMapper.map(fs.getAcademic_year(), AcademicYearMiniDto.class)
//                    ))
//                    .collect(Collectors.toList());
//        } catch (DataIntegrityViolationException e) {
//            throw new BadRequestException(e.getMostSpecificCause().getMessage());
//        }
//        return new ApiResponse(
//                feeStructureDtoList,
//                "School Fee Structure",
//                HttpStatus.OK.value());
//    }

    @PostAuthorize("hasAuthority(\"can_delete_fee_structure\")")
    @DeleteMapping("/fees-structure/year/{academic_year}")
    public ApiResponse deleteFeeStructure(@PathVariable("academic_year") String academic_year, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        try {
            feeStructureService.deletePerYear(UUID.fromString(academic_year), user.getOrganization().getOrg_id());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(null, "School Fee Deleted", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_fee_structure\")")
    @DeleteMapping("/fees-structure/class/{class_id}")
    public ApiResponse deleteFeeStructurePerClass(@PathVariable("class_id") String class_id, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        try {
            feeStructureService.deletePerClass(UUID.fromString(class_id), user.getOrganization().getOrg_id());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(null, "Class Fee Structure Deleted", HttpStatus.OK.value());
    }



}
