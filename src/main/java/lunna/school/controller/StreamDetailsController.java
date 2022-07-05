package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.StreamDetailsDto;
import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolStream;
import lunna.school.model.StreamDetails;
import lunna.school.model.User;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.*;
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
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 09. Jul 2021 9:29 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StreamDetailsController {

    final UserService userService;
    final SchoolService schoolService;
    final IService<SchoolStream>  schoolStreamIService;
    final StreamDetailsService streamDetailsService;
    final
    StaffService staffService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    JwtUtils utils;

    @Autowired
    public StreamDetailsController(UserService userService,
                                   SchoolService schoolService, IService<SchoolStream>  schoolStreamIService,
                                   StreamDetailsService streamDetailsService, StaffService staffService) {

        this.userService = userService;
        this.schoolService = schoolService;
        this.schoolStreamIService = schoolStreamIService;
        this.streamDetailsService = streamDetailsService;
        this.staffService = staffService;
    }
    @PostAuthorize("hasAuthority(\"can_view_stream_details\")")
    @GetMapping("/stream_details")
    public ApiResponse getStreamDetails(HttpServletRequest request) {
        List<StreamDetailsDto> streamDetailsDtos = null;
        if (request.isUserInRole("ROLE_Super Admin")){
            streamDetailsDtos = streamDetailsService.listAll()
                    .stream()
                    .map(stream -> modelMapper.map(stream, StreamDetailsDto.class))
                    .collect(Collectors.toList());
        }else {
            streamDetailsDtos = streamDetailsService.getByStreamSchoolOrgId(utils.getSchoolId(request))
                    .stream()
                    .map(stream -> modelMapper.map(stream, StreamDetailsDto.class))
                    .collect(Collectors.toList());
        }

        return new ApiResponse(
                streamDetailsDtos,
                "StreamDetails List",
                HttpStatus.OK.value()
        );
    }
    @PostAuthorize("hasAuthority(\"can_add_stream_details\")")
    @PostMapping("/stream_details/{school_stream_id}/create")
    public ApiResponse createStreamDetails(@RequestBody StreamDetails streamDetails,
                                           @PathVariable String school_stream_id,
                                           Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (streamDetails.getOrganization() == null) {
            streamDetails.setOrganization(user.getOrganization());
        }
        if (streamDetails.getStaff() == null) {
            throw new BadRequestException("Staff should not be null");
        }
        staffService.getStaffById(streamDetails.getStaff().getStaff_id());
        if (streamDetails.getSchool_subject() == null) {
            throw new BadRequestException("Subject should not be null");
        }

        SchoolStream SchoolStream = schoolStreamIService.getById(UUID.fromString(school_stream_id));
        if (!user.getOrganization().getOrg_id().equals(SchoolStream.getOrganization().getOrg_id())){
            throw new BadRequestException("You can only create class details for your school");
        }
        streamDetails.setSchool_stream(SchoolStream);
        StreamDetails streamDetails1;
        try{
            streamDetails1 = streamDetailsService.saveOrUpdate(streamDetails);
        }catch (DataIntegrityViolationException e){
            throw  new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                modelMapper.map(streamDetails1, StreamDetailsDto.class),
                "StreamDetails Created",
                HttpStatus.OK.value()
        );
    }


}
