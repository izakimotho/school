package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 23. Jul 2021 3:25 PM
 **/
@Getter
@Setter
public class SchoolStreamDetailsDto {
    private UUID school_stream_id;

    private ClassModel class_model;

    private SchoolStream school_stream;

    private Integer class_capacity;

    private Student class_rep;

    private StaffInfoDto class_teacher;

    public List<Student> studentList = new ArrayList<Student>();

    public List<StreamDetailsDto> class_subjects = new ArrayList<StreamDetailsDto>();

    public SchoolStreamDetailsDto(){}

}
