package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 6/13/22, Monday
 **/
@Getter
@Setter
public class FeeVoteHeadDTO {
    private String fee_structure_id;
    private String vote_head_name;
    private double amount;
    public FeeVoteHeadDTO(){}

    public FeeVoteHeadDTO(String fee_structure_id,
                          String vote_head_name,
                          double amount) {
        this.fee_structure_id = fee_structure_id;
        this.vote_head_name = vote_head_name;
        this.amount = amount;
    }

}
