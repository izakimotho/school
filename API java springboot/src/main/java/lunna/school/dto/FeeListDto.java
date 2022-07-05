package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.ClassModel;
import lunna.school.model.SchoolStream;
import lunna.school.model.Student;
import lunna.school.model.Terms;

/**
 * Collins K. Sang
 * 5/17/22 10:04 AM
 * school
 * FeeListDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeListDto {

    private StudentDto student_id;
    private double paid_amount;
    private double fee_amount;
    private double balance;
    private Terms terms;
    private ClassModelDto class_model;
    private SchoolStreamDto school_stream;
    private int year;

    public FeeListDto() {
    }
}
