package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.AcademicYear;
import lunna.school.model.ClassModel;
import lunna.school.model.Terms;

import java.util.List;

/**
 * Collins K. Sang
 * 5/30/22 1:48 PM
 * school
 * FeeStructureDetailsDtos
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeStructureDetailsDtos {
    private String fee_structure_id;
    private AcademicYearDto academic_year;
    private ClassModelDto class_model;
    private List<TermsDto> terms;
    private double total_amount;

    public FeeStructureDetailsDtos(String fee_structure_id, AcademicYearDto academic_year, ClassModelDto class_model, List<TermsDto> terms, double total_amount) {
        this.academic_year = academic_year;
        this.class_model = class_model;
        this.terms = terms;
        this.total_amount = total_amount;
        this.fee_structure_id = fee_structure_id;
    }
}
