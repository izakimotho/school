package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Organization;

/**
 * Collins K. Sang
 * 5/10/22 1:11 PM
 * school
 * ExamTypeDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class ExamTypeDto {
    private String exam_type_name;
    private String description;
}
