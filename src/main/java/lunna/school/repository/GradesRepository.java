package lunna.school.repository;

import lunna.school.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * GradesRepository
 *
 * @author Collins K. Sang
 * 2021/07/07 14:15
 **/
public interface GradesRepository extends JpaRepository<Grades, UUID> {
    @Query("SELECT g FROM Grades g WHERE g.grade_name=?1")
    Grades getByName(String grade_name);

    @Query("SELECT gr FROM Grades gr WHERE gr.organization.org_id=?1")
    List<Grades> getBySchool_id(UUID school_id);
}
