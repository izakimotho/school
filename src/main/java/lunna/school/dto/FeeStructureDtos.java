package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 6/10/22 11:31 AM
 * school
 * FeeStructureDtos
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeStructureDtos {
    private String fee_structure_id;
    private double fee_total_amount;
    private List<TermFeeDto> term_fee;
    private ClassModelDto class_model;
    private AcademicYearMiniDto academic_year;

    public FeeStructureDtos(UUID fee_structure_id, double fee_total_amount, List<TermFeeDto> term_fee, ClassModelDto class_model, AcademicYearMiniDto academic_year) {
        this.fee_structure_id = fee_structure_id.toString();
        this.fee_total_amount = fee_total_amount;
        this.term_fee = term_fee;
        this.class_model = class_model;
        this.academic_year = academic_year;
    }

//    public FeeStructureDtos(UUID fee_structure_id, double fee_total_amount, ClassModel class_model, AcademicYear academic_year) {
//        this.fee_structure_id = fee_structure_id.toString();
//        this.fee_total_amount = fee_total_amount;
//        this.class_model = modelMapper.map(class_model, ClassModelDto.class);
//        this.academic_year = modelMapper.map(academic_year, AcademicYearDto.class);
//
//    }
}
