package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Parent;
import lunna.school.model.Student;
import lunna.school.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 4/26/22, Tuesday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ParentController {
    @Autowired
    public ParentService parentService;

    @GetMapping("/parents/school/{school_id}")
    public ApiResponse schoolParents(@PathVariable("school_id") String school_id) {
        List<Parent> parentList = null;
        try{
            parentList = parentService.schoolParents(UUID.fromString(school_id));
        }catch (Exception e){
            throw  new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                parentList,
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/parents/student/{student_id}")
    public ApiResponse studentParent(@PathVariable("student_id") String student_id){
        Parent parent = null;
        try{
            parent = parentService.studentParent(UUID.fromString(student_id));
        }catch (Exception e){
            throw  new BadRequestException(e.getLocalizedMessage());
        }
        return new ApiResponse(
                parent,
                "",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/parents/school/{school_id}/class/{class_id}")
    public ApiResponse classStudents(@PathVariable("school_id") String school_id,
                                     @PathVariable("class_id") String class_id){
        List<Parent> parents = null;
                try{
                    parents = parentService.classParents(UUID.fromString(school_id),UUID.fromString(class_id));
                }catch (Exception e){
                    throw new BadRequestException(e.getLocalizedMessage());
                }
        return new ApiResponse(
                parents,
                "",
                HttpStatus.OK.value()
        );
    }
}
