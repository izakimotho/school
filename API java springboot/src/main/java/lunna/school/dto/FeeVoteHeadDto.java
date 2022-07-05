package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Terms;

import java.util.UUID;

/**
 * Collins K. Sang
 * 5/19/22 11:05 AM
 * school
 * FeeVoteHeadDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeVoteHeadDto {
    private String fee_vote_id;
    private String vote_head_name;
    private String description;
}
