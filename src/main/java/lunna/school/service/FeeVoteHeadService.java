package lunna.school.service;

import lunna.school.model.VoteHead;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 9:34 AM
 * school
 * FeeVoteHeadService
 * IntelliJ IDEA
 **/
public interface FeeVoteHeadService extends IService<VoteHead>{
    List<VoteHead> findBySchoolId(UUID org_id);

//    List<FeeVoteHead> findByTermId(UUID term_id);
}
