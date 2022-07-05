package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.AcademicYear;
import lunna.school.model.ClassModel;
import lunna.school.model.Terms;

import java.util.UUID;

/**
 * Collins K. Sang
 * 5/30/22 12:10 PM
 * school
 * FeeStructureDetailsDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeStructureDetailsDto {
    private String fee_structure_id;
    private AcademicYear academicYear;
    private ClassModel classModel;
    private Terms terms;
    private double totalAmount;

    public FeeStructureDetailsDto(){}

    public FeeStructureDetailsDto(AcademicYear academicYearDto, ClassModel classModelDto, Terms termsDto, double total_amount) {
        this.academicYear = academicYearDto;
        this.classModel = classModelDto;
        this.terms = termsDto;
        this.totalAmount = total_amount;
    }

    public FeeStructureDetailsDto(double amount, ClassModel class_model, AcademicYear academic_year_id, UUID fee_structure_id){
        this.totalAmount = amount;
        this.classModel = class_model;
        this.academicYear = academic_year_id;
        this.fee_structure_id = fee_structure_id.toString();
    }

}
