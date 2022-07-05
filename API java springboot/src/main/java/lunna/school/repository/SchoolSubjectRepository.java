package lunna.school.repository;

import lunna.school.model.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/16/22, Monday
 **/
public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, UUID> {

    @Query(value = "SELECT ss FROM SchoolSubject ss WHERE ss.organization.org_id =?1")
    List<SchoolSubject> findSubjectBySchool(UUID orgId);
    @Query(value = "SELECT count(ss) FROM SchoolSubject ss WHERE ss.organization.org_id =?1")
    Long count(UUID orgId);
}
