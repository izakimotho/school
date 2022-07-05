package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Collins K. Sang
 * 5/19/22 11:03 AM
 * school
 * FeeStructureDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeStructureDto {
    private String fee_structure_id;
    private float amount;
    private FeeVoteHeadDto fee_vote;
    private ClassModelDto class_model;
    private AcademicYearDto academic_year;

    private TermDetailsDtos term;

    public FeeStructureDto() {
    }

    public FeeStructureDto(String fee_structure_id, float amount, FeeVoteHeadDto fee_vote, ClassModelDto class_model, AcademicYearDto academic_year) {
        this.fee_structure_id = fee_structure_id;
        this.amount = amount;
        this.fee_vote = fee_vote;
        this.class_model = class_model;
        this.academic_year = academic_year;
    }

    public FeeStructureDto(String fee_structure_id, float amount, FeeVoteHeadDto fee_vote, ClassModelDto class_model, AcademicYearDto academic_year, TermDetailsDtos term) {
        this.fee_structure_id = fee_structure_id;
        this.amount = amount;
        this.fee_vote = fee_vote;
        this.class_model = class_model;
        this.academic_year = academic_year;
        this.term = term;
    }
}
