package lunna.school.repository;

import lunna.school.model.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 10:30 AM
 * school
 * TermsRepository
 * IntelliJ IDEA
 **/
@Repository
public interface TermsRepository extends JpaRepository<Terms, UUID> {

    @Query("SELECT t FROM Terms t WHERE t.organization.org_id = ?1")
    List<Terms> findBySchoolId(UUID org_id);
}
