package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.UserLevel;
import lunna.school.service.impl.UserLevelServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 12:09 PM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserCategoryController {
    final
    UserLevelServiceImpl userCategoryServiceImpl;
    public UserCategoryController(UserLevelServiceImpl userCategoryServiceImpl) {
        this.userCategoryServiceImpl = userCategoryServiceImpl;
    }
    @PostAuthorize("hasAuthority(\"can_view_user_category\")")
    @GetMapping("/user_categories")
    public ApiResponse getUserCategory(){
        List<UserLevel> userLevel;
        try{
            userLevel =  userCategoryServiceImpl.listAll();
        }catch (Exception e){
            throw new RecordNotFoundException(e.getMessage());
        }

        return new ApiResponse(
                userLevel,
                "",
                HttpStatus.OK.value()
        );

    }

    @PostAuthorize("hasAuthority(\"can_add_user_category\")")
    @PostMapping("/user_category/create")
    public ApiResponse createUserCategory(@RequestBody UserLevel userLevel){
        UserLevel userLevel1 = null;
        try{
            userLevel1 =  userCategoryServiceImpl.saveOrUpdate(userLevel);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                userLevel1,
                "",
                HttpStatus.OK.value()
        );

    }
}
