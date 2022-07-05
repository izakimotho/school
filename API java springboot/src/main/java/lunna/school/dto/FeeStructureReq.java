package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.AcademicYear;
import lunna.school.model.ClassModel;
import lunna.school.model.TermDetails;
import lunna.school.model.VoteHead;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 6/15/22, Wednesday
 **/
@Getter
@Setter
@ToString
public class FeeStructureReq {
    private String fee_structure_id;
    private double amount;
    private VoteHead voteHead;
    private ClassModel classModel;
    private AcademicYear academicYear;

    private TermDetails termDetails;

    public FeeStructureReq() {
    }
}
