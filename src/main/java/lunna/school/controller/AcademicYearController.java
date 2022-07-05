package lunna.school.controller;

import lunna.school.dto.AcademicYearDto;
import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.AcademicYear;
import lunna.school.model.Organization;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.AcademicYearService;
import lunna.school.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AcademicYearController {
    @Autowired
    AcademicYearService academicYearService;
    @Autowired
    SchoolService schoolService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    JwtUtils utils;

    @PostAuthorize("hasAuthority(\"can_add_academic_year\")")
    @PostMapping("/academic_year")
    public ApiResponse create(@RequestBody AcademicYear academicYear,
                              HttpServletRequest request){
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));
        academicYear.setOrganization(org);
        AcademicYear academicYear1;
        try {
            academicYear1 = academicYearService.saveOrUpdate(academicYear);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                modelMapper.map(academicYear1, AcademicYearDto.class),
                "Created successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_academic_year\")")
    @GetMapping("/academic_year")
    public ApiResponse list(HttpServletRequest request){
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));
        List<AcademicYearDto> academicYears = academicYearService.getAcademicYearBySchool(org.getOrg_id())
                .stream()
                .map(academicYear -> modelMapper.map(academicYear, AcademicYearDto.class))
                .collect(Collectors.toList());

        return new ApiResponse(
                academicYears,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_academic_year\")")
    @GetMapping("/academic_year/{id}")
    public ApiResponse getById(@PathVariable("id") String id){
        AcademicYearDto academicYears = modelMapper.map(academicYearService.getById(UUID.fromString(id)), AcademicYearDto.class);
        return new ApiResponse(
                academicYears,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_academic_year\")")
    @PutMapping("/academic_year")
    public ApiResponse update(@RequestBody AcademicYear academicYear,
                              HttpServletRequest request){
        Organization org = schoolService.getSchoolById(utils.getSchoolId(request));
        academicYear.setOrganization(org);
        AcademicYear academicYear1;
        try {
            academicYear1 = academicYearService.saveOrUpdate(academicYear);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                modelMapper.map(academicYear1, AcademicYearDto.class),
                "Updated",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_academic_year\")")
    @DeleteMapping("/academic_year/{academic_year_id}")
    public ApiResponse delete(@PathVariable("academic_year_id") String academic_year_id){
        AcademicYear academicYear = academicYearService.getById(UUID.fromString(academic_year_id));
        if (academicYear == null){
            throw new RecordNotFoundException("NOT FOUND");
        }

        academicYearService.delete(academicYear);

        return new ApiResponse(
                null,
                "Deleted",
                HttpStatus.OK.value()
        );
    }

}
