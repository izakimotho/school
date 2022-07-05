package lunna.school.repository;

import lunna.school.model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * ClassesRepository
 *
 * @author Collins K. Sang
 * 2021/07/02 13:17
 **/

public interface ClassesRepository extends JpaRepository<ClassModel, UUID> {

    List<ClassModel> findAllByDeletedAtIsNull();

    @Query("SELECT cn FROM ClassModel cn WHERE cn.class_name=?1")
    ClassModel getByName(String class_name);

    @Query(value = "SELECT * FROM class_model c WHERE ( c.education_system_id = ?1)" +
            "and (?2 is null or cast(c.school_level_id as VARCHAR) = cast(?2 as VARCHAR))" ,nativeQuery = true)
    List<ClassModel> filterByEducationSystemAndSchoolLevel(UUID educationSystem, UUID schoolLevel);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ClassModel c SET c.deletedAt = CURRENT_DATE WHERE c.class_id =?1")
    void deleteClass(UUID class_id);

}
