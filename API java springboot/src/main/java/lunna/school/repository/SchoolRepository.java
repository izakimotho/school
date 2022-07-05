package lunna.school.repository;

import lunna.school.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:37 AM
 **/

public interface SchoolRepository extends JpaRepository<Organization, UUID> {
    List<Organization> findAllByDeletedAtIsNull();

    @Query("SELECT o FROM Organization o WHERE o.org_id =?1 and o.deletedAt = null")
    List<Organization> findByOrgIdAndDeletedAtIsNull(UUID id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Organization org SET org.deletedAt = CURRENT_DATE WHERE org.org_id =?1")
    void deleteSchool(UUID org_id);

}
