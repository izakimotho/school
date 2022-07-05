package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.*;

import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/10/22 11:34 AM
 * school
 * GradePostingDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class GradePostingDto {
    private UUID grade_posting_id;
    private float grade;
    private String remarks;
    private Date createAt = new Date();
    private ExamScheduleDto exam_schedule;
    private StudentDto student;
    private SchoolDto organization;
    private UserDto posted_by;
}
