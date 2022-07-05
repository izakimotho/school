package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.UserType;
import lunna.school.service.IService;
import lunna.school.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserTypeController {
    @Autowired
    UserTypeService userTypeService;

    @PostMapping("/user_type")
    public ApiResponse create(@RequestBody UserType userType){
        return new ApiResponse(
                userTypeService.saveOrUpdate(userType),
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/user_types")
    public ApiResponse listUserTypes(){
        return new ApiResponse(
                userTypeService.listAll(),
                "",
                HttpStatus.OK.value()
        );
    }
    @PutMapping("/user_type")
    public ApiResponse update(@RequestBody UserType userType){
        UserType userType1 = userTypeService.getById(userType.getUser_type_id());
        userType1.setUser_type_name(userType.getUser_type_name());
        return new ApiResponse(
                userTypeService.saveOrUpdate(userType1),
                "",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/user_type/{user_type_id}")
    public ApiResponse delete(@PathVariable("user_type_id") Long user_type_id){
        UserType userType1 = userTypeService.getById(user_type_id);
        if (userType1 == null){
            throw new RecordNotFoundException("User Type Not FOUND");
        }
        userTypeService.delete(userType1);
        return new ApiResponse(
                null,
                "",
                HttpStatus.OK.value()
        );
    }
}
