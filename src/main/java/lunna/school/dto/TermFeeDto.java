package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.FeeStructure;

import java.util.List;

/**
 * Collins K. Sang
 * 6/10/22 11:34 AM
 * school
 * TermFeeDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class TermFeeDto {
    private FeeTermDto term;
    private double term_total_amount;

    public TermFeeDto(FeeTermDto term, double term_total_amount) {
        this.term = term;
        this.term_total_amount = term_total_amount;
    }
}
