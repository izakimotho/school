package lunna.school.repository;

import lunna.school.model.GenderEnum;
import lunna.school.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StaffRepository
 *
 * @author Collins K. Sang
 * 2021/06/30 10:18
 **/
@Transactional
public interface StaffRepository extends JpaRepository<Staff, UUID> {

    @Query("SELECT st FROM Staff st WHERE st.gender =?1 ORDER BY st.createdAt DESC")
    List<Staff> findAllByGender(GenderEnum gender);

    @Query("SELECT st FROM Staff st WHERE st.organization.org_id =?1 ORDER BY st.createdAt DESC")
    List<Staff> findAllBySchoolId(UUID school_id);

    @Query("SELECT st FROM Staff st WHERE st.email =?1")
    Staff findByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.positions.position_id=?1 AND s.organization.org_id=?2 ORDER BY s.createdAt DESC")
    Staff getStaffByPositionAndSchool(UUID position_id, UUID org_id);

    @Query("SELECT st FROM Staff st WHERE st.user.user_id =?1")
    Staff findByUserId(UUID user_id);

    @Query("SELECT count(st) FROM Staff st WHERE st.organization.org_id =?1")
    Long countByOrg(UUID id);
}
