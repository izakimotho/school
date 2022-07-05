package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/16/22 2:28 PM
 * school
 * StudentFeeDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class StudentFeeDto {

    private UUID student_fee_id;
    private Student student_id;
    private Organization org_id;
    private ClassModel class_id;
    private SchoolStream school_stream_id;
    private AcademicYearDto year;
    private Terms terms;
    private Float fee_amount;
    private Float balance;
    private User created_by;

    public StudentFeeDto() {
    }

    public StudentFeeDto(UUID student_fee_id, Student student_id, Organization org_id, ClassModel class_id, SchoolStream school_stream_id, AcademicYearDto year, Terms terms, Float fee_amount, Float balance, User created_by) {
        this.student_fee_id = student_fee_id;
        this.student_id = student_id;
        this.org_id = org_id;
        this.class_id = class_id;
        this.school_stream_id = school_stream_id;
        this.year = year;
        this.terms = terms;
        this.fee_amount = fee_amount;
        this.balance = balance;
        this.created_by = created_by;
    }
}
