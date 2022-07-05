package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/16/22, Monday
 **/
@Getter
@Setter
public class SchoolSubjectDto {
    private UUID school_subject_id;
    private SubjectDto subject;
    private SchoolDto organization;
    private Date createdAt;
    public SchoolSubjectDto(){}
}
