package lunna.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.VoteHead;

import java.util.ArrayList;
import java.util.List;

/**
 * Collins K. Sang
 * 6/10/22 11:52 AM
 * school
 * FeeTermDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeTermDto {
    private String term_name;
    @JsonIgnore
    private VoteHead vote_heads1;
    private List<VoteHeadDetailsDto> vote_heads;
//    private Float amount;


    public FeeTermDto(String term_name, List<VoteHeadDetailsDto> vote_heads1) {
        List<VoteHeadDetailsDto> vote_heads2 = new ArrayList<>();
        VoteHead voteHead;
        this.term_name = term_name;
        this.vote_heads = vote_heads1;


    }

    public FeeTermDto(VoteHead fee_vote, Float amount) {
        this.vote_heads1 = fee_vote;
//        this.amount = amount;
    }

    public FeeTermDto() {

    }
}
