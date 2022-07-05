package lunna.school.repository;

import lunna.school.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {
    @Query(value = "SELECT a FROM AcademicYear a WHERE a.organization.org_id = ?1")
    List<AcademicYear> findAllBySchoolId(UUID school_id);

    @Query(value = "SELECT a FROM AcademicYear a WHERE a.organization.org_id = ?1 AND a.status=true")
    AcademicYear findActiveBySchoolId(UUID school_id);
}
