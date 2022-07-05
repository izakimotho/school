package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.Sms;
import lunna.school.model.User;
import lunna.school.service.SmsService;
import lunna.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 18. Aug 2021 10:01 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SmsController {
    @Autowired
    SmsService smsService;
    @Autowired
    UserService userService;
    @GetMapping("/sms")
    public ApiResponse fetchAll(){
        return new ApiResponse(
                smsService.listAll(),
                "Sms List",
                HttpStatus.OK.value()
        );
    }

    @PostMapping("/sms")
    public ApiResponse create(@RequestBody Sms sms, Principal principal){
        User user = userService.findByUsername(principal.getName());
        if (user ==null){
            throw   new RecordNotFoundException("User not Found. Please login");
        }
        sms.setUser(user);
        Sms sms1 = smsService.saveOrUpdate(sms);
        return new ApiResponse(
                sms1,
                "Sms Created successfully",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/sms/{sms_id}")
    public ApiResponse getById(@PathVariable("sms_id") String sms_id, Principal principal){
        User user = userService.findByUsername(principal.getName());
        if (user ==null){
            throw   new RecordNotFoundException("User not Found. Please login");
        }
        return new ApiResponse(
                smsService.listAll(),
                "Sms List",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/sms/recipient")
    public ApiResponse getRecipients(Principal principal){
        User user = userService.findByUsername(principal.getName());
        if (user ==null){
            throw   new RecordNotFoundException("User not Found. Please login");
        }

        return new ApiResponse(
                smsService.recipient(user.getOrganization().getOrg_id()),
                "Sms recipient",
                HttpStatus.OK.value()
        );
    }
}
