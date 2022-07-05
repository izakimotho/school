package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.RoleDto;
import lunna.school.dto.UserDto;
import lunna.school.model.Fee;
import lunna.school.model.Terms;
import lunna.school.model.User;
import lunna.school.service.FeeService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/13/22 11:16 AM
 * school
 * FeeController
 * IntelliJ IDEA
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FeeController {
    final FeeService feeService;
    final UserService userService;
    ModelMapper modelMapper = new ModelMapper();
    ModelMapper modelMapper2 = new ModelMapper();

    @Autowired
    public FeeController(FeeService feeService, UserService userService) {
        this.feeService = feeService;
        this.userService = userService;
    }

    //create fee
    @PostMapping("/fees")
    public ApiResponse createFee(@RequestBody Fee fee, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        fee.setCreated_by(user);
        return new ApiResponse(
                feeService.saveOrUpdate(fee),
                "Fee saved successfully!",
                HttpStatus.OK.value()
        );
    }

    //update fee
    @PutMapping("/update")
    public ApiResponse updateFee(@RequestBody Fee fee) {

        return new ApiResponse(
                feeService.saveOrUpdate(fee),
                "Fee Updated successfully!",
                HttpStatus.OK.value()
        );
    }

    //view all fees
    @GetMapping("/list")
    public ApiResponse listFee(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        RoleDto roleDto = modelMapper2.map(userDto.getRoles(), RoleDto.class);

        if (Objects.equals(roleDto.getRole_name(), "Super Admin")) {
            return new ApiResponse(
                    feeService.listAll(),
                    "",
                    HttpStatus.OK.value()
            );
        } else {
            return new ApiResponse(
                    feeService.ListPerSchool(user.getOrganization().getOrg_id()),
                    "",
                    HttpStatus.OK.value()
            );
        }
    }

    //view fee per term
    @GetMapping("/list/{term}/term")
    public ApiResponse listFee(@PathVariable("term") Terms term, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        RoleDto roleDto = modelMapper2.map(userDto.getRoles(), RoleDto.class);

        if (Objects.equals(roleDto.getRole_name(), "Super Admin")) {
            return new ApiResponse(
                    feeService.ListPerTerm(term),
                    String.format("Term %s Fee list", term),
                    HttpStatus.OK.value()
            );
        } else {
            return new ApiResponse(
                    feeService.ListPerTermPerSchool(term, user.getOrganization().getOrg_id()),
                    String.format("Term %s Fee list", term),
                    HttpStatus.OK.value()
            );
        }
    }
    //view fee per class

    @GetMapping("/list/{class_id}/class")
    public ApiResponse classFee(@PathVariable("class_id") String class_id) {
        return new ApiResponse(
                feeService.ListPerClass(UUID.fromString(class_id)),
                "Class Fee Structure List",
                HttpStatus.OK.value()
        );
    }

    //view fee per school
    @GetMapping("/list/{school_id}/school")
    public ApiResponse schoolFee(@PathVariable("school_id") String school_id) {
        return new ApiResponse(
                feeService.ListPerSchool(UUID.fromString(school_id)),
                "School Fee Structure List",
                HttpStatus.OK.value()
        );
    }

    //delete fee
    @DeleteMapping("/delete/{fee_id}")
    public ApiResponse deleteFee(@PathVariable("fee_id") String fee_id) {
        Fee fee = feeService.getById(UUID.fromString(fee_id));
        feeService.delete(fee);
        return new ApiResponse(
                fee,
                "Deleted successfully",
                HttpStatus.OK.value()
        );
    }
}
