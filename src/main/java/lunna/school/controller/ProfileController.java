package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.ProfileDto;
import lunna.school.model.Staff;
import lunna.school.model.User;
import lunna.school.model.UserLevel;
import lunna.school.service.MinioAdapter;
import lunna.school.service.StaffService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URLConnection;
import java.security.Principal;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProfileController {
    @Autowired
    UserService userService;
    @Autowired
    StaffService staffService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    MinioAdapter minioAdapter;

    @GetMapping("/profile")
    public ApiResponse getProfile(Principal principal,
                                  HttpServletRequest request) throws Exception {
        User user;
        ProfileDto profileDto;

        try{
            user = userService.findByUsername(principal.getName());
            UserLevel userLevel = user.getUser_category();
            profileDto = modelMapper.map(user, ProfileDto.class);
            Staff staff;
            switch (userLevel.getUser_level()) {
                case 2:
                    staff = staffService.getStaffByUserId(user.getUser_id());
                    profileDto.setPhone_number(staff.getPhone_number());
                    profileDto.setGender(staff.getGender());
                    break;
                default:
                    break;


            }
//            String avatar_url = user.getAvatar();
//            String logo_url = user.getOrganization().getLogo();
//            if(avatar_url != null){
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(ProfileController.class, "getFile", avatar_url).build().toString();
//                profileDto.setAvatar(url);
//            }
//            if(logo_url != null){
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(ProfileController.class, "getFile", logo_url).build().toString();
//                profileDto.getOrganization().setLogo(url);
//            }

        }catch (Exception e){
            throw new Exception(e.getLocalizedMessage());
        }

        return new ApiResponse(
                profileDto,
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/profile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        byte[] data = minioAdapter.getFile(filename);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

}
