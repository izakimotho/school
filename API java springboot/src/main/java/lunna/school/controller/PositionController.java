package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.PositionDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.service.SchoolService;
import lunna.school.service.StaffService;
import lunna.school.service.UserService;
import lunna.school.service.UserTypeService;
import lunna.school.service.impl.PositionServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * PositionController
 *
 * @author Collins K. Sang
 * 2021/07/08 14:44
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PositionController {

    final PositionServiceImpl positionServiceImpl;
    final SchoolService schoolService;
    final UserService userService;
    final StaffService staffService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    UserTypeService userTypeService;

    @Autowired
    public PositionController(PositionServiceImpl positionServiceImpl, SchoolService schoolService, UserService userService, StaffService staffService) {
        this.positionServiceImpl = positionServiceImpl;
        this.schoolService = schoolService;
        this.userService = userService;
        this.staffService = staffService;
    }

    //create positions
    @PostAuthorize("hasAuthority(\"can_add_positions\")")
    @PostMapping("/positions/create")
    public ApiResponse createPositions(@RequestBody Positions positions, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("Staff not found. Login as a staff");
        }
        Organization organization = null;
        if (positions.getOrganization() == null) {
            organization = schoolService.getSchoolById(user.getOrganization().getOrg_id());
        }

        positions.setOrganization(organization);
        positions.setCreated_by(user);
        Positions positions1 = null;
        try {
            positions1 = positionServiceImpl.saveOrUpdate(positions);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                modelMapper.map(positions1, PositionDto.class),
                "Position created Successfully",
                HttpStatus.OK.value());
    }

    //update positions
    @PostAuthorize("hasAuthority(\"can_edit_positions\")")
    @PutMapping("/positions/update")
    public ApiResponse updatePositions(@RequestBody Positions positions, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        if (staff == null) {
            throw new RecordNotFoundException("Staff not found. Login as a staff");
        }
        Organization organization = schoolService.getSchoolById(staff.getOrganization().getOrg_id());
        positions.setOrganization(organization);
        positions.setCreated_by(user);
        Positions positions1 = null;
        try {
            positions1 = positionServiceImpl.saveOrUpdate(positions);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                positions1,
                "Position updated Successfully",
                HttpStatus.OK.value());
    }

    //get all positions
    @PostAuthorize("hasAuthority(\"can_view_positions\")")
    @GetMapping("/positions/list")
    public ApiResponse getPositions(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        if (user == null) {
            throw new RecordNotFoundException("Staff not found. Login as a staff");
        }
        Organization organization = null;
        if (staff != null) {
            organization = schoolService.getSchoolById(user.getOrganization().getOrg_id());
        }
        List<PositionDto> positions;
        if (staff == null) {
            positions = positionServiceImpl.listAll()
                    .stream()
                    .map(s ->
                            modelMapper.map(s, PositionDto.class)

                    )
                    .collect(Collectors.toList());
        } else {
            positions = positionServiceImpl.getBySchool(organization.getOrg_id())
                    .stream()
                    .map(s ->
                            modelMapper.map(s, PositionDto.class)

                    )
                    .collect(Collectors.toList());
        }

        return new ApiResponse(
                positions,
                "",
                HttpStatus.OK.value());
    }

    //get positions per school
    @PostAuthorize("hasAuthority(\"can_view_positions\")")
    @GetMapping("/school/{school_id}/positions/list")
    public ApiResponse getPositionsPerSchool(@PathVariable("school_id") String school_id) {
        List<Positions> positions = positionServiceImpl.getBySchool(UUID.fromString(school_id))
                .stream()
                .map(s ->
                        modelMapper.map(s, Positions.class)

                )
                .collect(Collectors.toList());
        return new ApiResponse(
                positions,
                "",
                HttpStatus.OK.value());
    }

    //get positions per school per category
    @PostAuthorize("hasAuthority(\"can_view_positions\")")
    @GetMapping("/school/{school_id}/category/{category}/positions/list")
    public ApiResponse getPositionsPerSchoolAndCategory(@PathVariable("school_id") String school_id, @PathVariable("category") Long category) {
        UserType userType = userTypeService.getById(category);
        List<Positions> positions = positionServiceImpl.getPerSchoolAndCategory(UUID.fromString(school_id), userType)
                .stream()
                .map(s ->
                        modelMapper.map(s, Positions.class)
                )
                .collect(Collectors.toList());

        return new ApiResponse(
                positions,
                "",
                HttpStatus.OK.value());
    }

    //get positions per category
    @PostAuthorize("hasAuthority(\"can_view_positions\")")
    @GetMapping("/category/{category}/positions/list")
    public ApiResponse getPositionsPerCategory(@PathVariable("category") Long category) {
        UserType userType = userTypeService.getById(category);
        List<Positions> positions = positionServiceImpl.getPerCategory(userType)
                .stream()
                .map(s ->
                        modelMapper.map(s, Positions.class)
                )
                .collect(Collectors.toList());

        return new ApiResponse(
                positions,
                "",
                HttpStatus.OK.value());
    }

    //delete positions
    @PostAuthorize("hasAuthority(\"can_delete_positions\")")
    @GetMapping("/positions/{position_id}/delete")
    public ApiResponse deletePositions(@PathVariable("position_id") String position_id) {
        Positions positions = positionServiceImpl.getById(UUID.fromString(position_id));
        positionServiceImpl.delete(positions);
        return new ApiResponse(
                positions,
                "",
                HttpStatus.OK.value());
    }

}
