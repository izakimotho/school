package lunna.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 6/13/22, Monday
 **/
@Getter
@Setter
public class FeeTermDTO {
    private String term_name;
    private double term_total_amount;
    private List<FeeVoteHeadDTO> vote_heads;

    public FeeTermDTO(){}


    public double getTerm_total_amount(){
        for (FeeVoteHeadDTO feeVoteHeadDTO : vote_heads){
            this.term_total_amount += feeVoteHeadDTO.getAmount();
        }
        return this.term_total_amount;
    }
    @JsonIgnore
    public double getTermTotalAmount(){
        return this.term_total_amount;
    }


}
