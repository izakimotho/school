package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Organization;
import lunna.school.model.Staff;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@mail.com
 * @created : 4/25/22, Monday
 **/
@Getter
@Setter
public class SchoolStreamDto {
    private UUID school_stream_id;
    private Integer class_capacity;
    String stream_name;
    String abbr;
    String description;
    private ClassModelDto class_model;
    private StudentDto class_rep;
    private StaffDto class_teacher;
    private SchoolDto organization;

    public SchoolStreamDto() {
    }
}
