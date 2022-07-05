package lunna.school.repository;

import lunna.school.model.TermDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 2:17 PM
 * school
 * TermDetailsRepository
 * IntelliJ IDEA
 **/
@Repository
public interface TermDetailsRepository extends JpaRepository<TermDetails, UUID> {

    @Query("SELECT td FROM TermDetails td WHERE td.terms.organization.org_id=?1")
    List<TermDetails> findBySchoolId(UUID org_id);
}
