package lunna.school.repository;

import lunna.school.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 11:06 AM
 **/

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    @Query("SELECT s FROM Subject s WHERE s.subject_name=?1")
    Subject getByName(String subject_name);

    @Query(value = "SELECT * FROM subjects s WHERE ( s.education_system_id = ?1)" +
            "and (?2 is null or cast(s.school_level_id as VARCHAR) = cast(?2 as VARCHAR))" ,nativeQuery = true)
    List<Subject> filterByEducationSystemAndSchoolLevel(UUID educationSystem, UUID schoolLevel);

    @Query(value = "SELECT s FROM Subject  s where s.school_level.school_level_id =?1 and s.deletedAt is null ")
    List<Subject> findAllBySchoolLevel(UUID schoolLevel);

    @Query(value = "SELECT s FROM Subject  s where s.education_system.education_system_id =?1 and s.deletedAt is null ")
    List<Subject> findAllByEducationSystem(UUID educationSystem);


}
