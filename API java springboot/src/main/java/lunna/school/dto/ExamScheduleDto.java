package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.ExamType;
import lunna.school.model.SchoolSubject;
import lunna.school.model.Subject;
import lunna.school.model.Terms;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/10/22 12:09 PM
 * school
 * ExamScheduleDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class ExamScheduleDto {
    private UUID exam_schedule_id;
    private TermsDto terms;
    private String exam_schedule_name;
    private Date exam_date;
    private LocalTime exam_time;
    private ExamTypeDto exam_type;
    private SchoolSubjectDto subject;
    private SchoolStreamDto school_stream;

}
