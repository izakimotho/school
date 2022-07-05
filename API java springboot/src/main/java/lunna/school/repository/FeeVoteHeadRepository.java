package lunna.school.repository;

import lunna.school.model.VoteHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 9:35 AM
 * school
 * FeeVoteHeadRepository
 * IntelliJ IDEA
 **/
@Repository
public interface FeeVoteHeadRepository extends JpaRepository<VoteHead, UUID> {

    @Query("SELECT fv FROM VoteHead fv WHERE fv.organization.org_id =?1")
    List<VoteHead> findBySchoolId(UUID org_id);
//    @Query("SELECT fv FROM FeeVoteHead fv WHERE fv.fee_vote_id =?1")
//    List<FeeVoteHead> findByTermId(UUID term_id);
}
