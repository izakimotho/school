package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.AcademicYear;
import lunna.school.model.ClassModel;

import java.util.List;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 6/13/22, Monday
 **/
@Getter
@Setter
public class FeeStructureDTO {
    private List<FeeTermDTO> term_fee;
    private double fee_total_amount;
    private ClassModelDto class_model;
    private AcademicYearDto academic_year;

    public FeeStructureDTO() {
    }

    public FeeStructureDTO(List<FeeTermDTO> term_fee,
                           ClassModelDto class_model,
                           AcademicYearDto academic_year) {
        this.term_fee = term_fee;
        this.class_model = class_model;
        this.academic_year = academic_year;
    }

    public double getFee_total_amount(){
        for (FeeTermDTO feeTermDTO : term_fee){
            this.fee_total_amount += feeTermDTO.getTermTotalAmount();
        }
        return this.fee_total_amount;
    }
}
