package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.*;
import org.modelmapper.ModelMapper;

/**
 * Collins K. Sang
 * 5/16/22 4:24 PM
 * school
 * FeeRecordDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeRecordDto {
    ModelMapper modelMapper = new ModelMapper();
    private Student student_id;
    private double paid_amount;
    private double balance;
    private TermDetailsDtos terms;
    private ClassModel class_model;
    private SchoolStream school_stream;

    private  AcademicYearDto academic_year;
    private double fee_amount;

    public FeeRecordDto() {
    }

    public FeeRecordDto(Student student_id, double paid_amount, TermDetails terms, ClassModel class_model, SchoolStream school_stream, AcademicYear academic_year) {
        this.student_id = student_id;
        this.paid_amount = paid_amount;
        this.terms = modelMapper.map(terms, TermDetailsDtos.class);;
        this.class_model = class_model;
        this.school_stream = school_stream;
        this.academic_year = modelMapper.map(academic_year, AcademicYearDto.class);
    }

    public FeeRecordDto(Student student_id, double paid_amount, double balance, double fee_amount, TermDetails terms, ClassModel class_model, SchoolStream school_stream, AcademicYear academic_year) {
        this.student_id = student_id;
        this.paid_amount = paid_amount;
        this.balance = balance;
        this.terms = modelMapper.map(terms, TermDetailsDtos.class);
        this.class_model = class_model;
        this.school_stream = school_stream;
        this.academic_year = modelMapper.map(academic_year, AcademicYearDto.class);
        this.fee_amount = fee_amount;
    }
}
