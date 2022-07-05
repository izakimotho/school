package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.HostelDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.LockedException;
import lunna.school.model.*;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.HostelService;
import lunna.school.service.SchoolService;
import lunna.school.service.StaffService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * HostelController
 *
 * @author Collins K. Sang
 * 2021/07/02 11:31
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HostelController {
    final HostelService hostelService;
    final ModelMapper modelMapper = new ModelMapper();
    final SchoolService schoolService;
    final UserService userService;
    final StaffService staffService;

    @Autowired
    JwtUtils utils;

    @Autowired
    public HostelController(HostelService hostelService, SchoolService schoolService, UserService userService, StaffService staffService) {
        this.hostelService = hostelService;
        this.schoolService = schoolService;
        this.userService = userService;
        this.staffService = staffService;
    }


    @PostAuthorize("hasAuthority(\"can_view_hostels\")")
    @GetMapping("/hostel/list")
    public ApiResponse getHostelList(HttpServletRequest request) {
        List<HostelDto> hostelDtoList = new ArrayList<>();
        try {
            hostelDtoList = hostelService.getListPerSchool(utils.getSchoolId(request))
                    .stream()
                    .map(s ->
                            modelMapper.map(s, HostelDto.class)

                    )
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                hostelDtoList,
                "",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_hostels\")")
    @GetMapping("/school/{school_code}/hostels")
    public ApiResponse getHostelsPerSchool(@PathVariable("school_code") String school_code) {
        Organization organization = schoolService.getSchoolById(UUID.fromString(school_code));

        List<HostelDto> hostelDtoList = hostelService.getListPerSchool(organization)
                .stream()
                .map(s ->
                        modelMapper.map(s, HostelDto.class)

                )
                .collect(Collectors.toList());
        return new ApiResponse(
                hostelDtoList,
                "",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_hostels\")")
    @GetMapping("/school/hostels")
    public ApiResponse getHostelsInOwnSchool(HttpServletRequest request) {

        List<HostelDto> hostelDtoList = new ArrayList<>();
        try {
            Organization organization = schoolService.getSchoolById(utils.getSchoolId(request));
            hostelDtoList = hostelService.getListPerSchool(organization)
                    .stream()
                    .map(s ->
                            modelMapper.map(s, HostelDto.class)
                    )
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                hostelDtoList,
                "",
                HttpStatus.OK.value());

    }

    @PostAuthorize("hasAuthority(\"can_view_hostels\")")
    @GetMapping("/school/hostels/{hostel_name}")
    public ApiResponse getHostelsInOwnSchool(@PathVariable("hostel_name") String hostel_name) {
        return new ApiResponse(
                modelMapper.map(hostelService.getPerName(hostel_name), HostelDto.class),
                "",
                HttpStatus.OK.value());

    }

    @PostAuthorize("hasAuthority(\"can_add_hostels\")")
    @PostMapping("/hostel/create")
    public ApiResponse createHostel(@RequestBody HostelDto hostelDto, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Staff staff = staffService.getStaffByEmail(user.getEmail());

        if (staff == null) {
            throw new BadRequestException("Can't Create Hostel. You are not a staff under any school");
        }

        Staff hostel_master = hostelDto.getHostel_master() != null ? hostelDto.getHostel_master() : null;

        Student hostel_captain = hostelDto.getHostel_captain() != null ? hostelDto.getHostel_captain() : null;

        Organization organization = schoolService.getSchoolById(staff.getOrganization().getOrg_id());

        Hostel isExistStudentLeader = hostelService.getIfExist(hostel_captain, organization, hostelDto.getHostel_name());

        Hostel isExistTeacherLeader = hostelService.getIfExistHostelMaster(hostel_master, organization, hostelDto.getHostel_name());

        if (isExistStudentLeader != null) {
            throw new LockedException("The Student Leadership position is already taken." +
                    " kindly try a different title or Hostel");
        }
        if (isExistTeacherLeader != null) {
            throw new LockedException("The Hostel Master Leadership position is already taken. kindly try a different title or Hostel");
        }

        Hostel hostel = new Hostel(
                hostelDto.getHostel_name(),
                hostelDto.getHostel_capacity(),
                hostelDto.getDescription(),
                organization,
                hostel_captain,
                hostel_master,
                username,
                new Date());

        Hostel hostel1 = hostelService.save(hostel);
        return new ApiResponse(
                modelMapper.map(hostel1, HostelDto.class),
                "Hostel Created Successfully",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_edit_hostels\")")
    @PutMapping("/hostel/{hostel_id}/update")
    public ApiResponse createHostel(@RequestBody HostelDto hostelDto, Principal principal, @PathVariable("hostel_id") String hostel_id) {
        Hostel hostelObject = hostelService.getById(hostel_id);
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        if (staff == null) {
            throw new BadRequestException("Can't Update Hostel. You are not a staff under any organization /school");
        }

        Organization organization = schoolService.getSchoolById(staff.getOrganization().getOrg_id());

        Staff hostel_master = hostelDto.getHostel_master() != null ? hostelDto.getHostel_master() : null;

        Student hostel_captain = hostelDto.getHostel_captain() != null ? hostelDto.getHostel_captain() : null;

        Hostel hostel = new Hostel(
                hostelObject.getHostel_id(),
                hostelDto.getHostel_name(),
                hostelDto.getHostel_capacity(),
                hostelDto.getDescription(),
                organization,
                hostel_captain,
                hostel_master,
                username,
                new Date());
        Hostel hostel1 = hostelService.update(hostel);
        return new ApiResponse(
                modelMapper.map(hostel1, HostelDto.class),
                "Hostel Updated Successfully",
                HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_hostels\")")
    @DeleteMapping("/hostel/{hostel_id}/delete")
    public ApiResponse deleteHostel(Principal principal, @PathVariable("hostel_id") String hostel_id) {
        Hostel hostelObject = hostelService.getById(hostel_id);
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Staff staff = staffService.getStaffByEmail(user.getEmail());
        if (staff == null) {
            throw new BadRequestException("Can't Delete Hostel. You are not a staff under any organization /school");
        }
        Organization organization = schoolService.getSchoolById(staff.getOrganization().getOrg_id());
        hostelObject.setOrg_id(organization);
        Hostel hostel1 = hostelService.delete(hostelObject);
        return new ApiResponse(
                modelMapper.map(hostel1, HostelDto.class),
                "Hostel Deleted Successfully",
                HttpStatus.OK.value());
    }

}
