package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Collins K. Sang
 * 6/13/22 10:28 AM
 * school
 * VoteHeadDetailsDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class VoteHeadDetailsDto {
    private FeeVoteHeadDto fee_vote;
    private Float amount;

    public VoteHeadDetailsDto(FeeVoteHeadDto fee_vote, Float amount) {
        this.fee_vote = fee_vote;
        this.amount = amount;
    }
}
