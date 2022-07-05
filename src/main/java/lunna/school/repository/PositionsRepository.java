package lunna.school.repository;

import lunna.school.model.Positions;
import lunna.school.model.UserType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * PositionsRepository
 *
 * @author Collins K. Sang
 * 2021/07/08 14:30
 **/
@Transactional(rollbackFor = DataIntegrityViolationException.class)
public interface PositionsRepository extends JpaRepository<Positions, UUID> {

    @Query("SELECT p FROM Positions p WHERE p.position_name=?1")
    Positions getByName(String position_name);

    @Query("SELECT p FROM Positions p WHERE p.organization.org_id=?1")
    List<Positions> getBySchool(UUID organization);

    @Query("SELECT p FROM Positions p WHERE p.organization.org_id=?1 AND p.position_holder=?2")
    List<Positions> getPerSchoolAndCategory(UUID organization, UserType category);

    @Query("SELECT p FROM Positions p WHERE p.position_holder=?1")
    List<Positions> getPerCategory(UserType category);
}
