package lunna.school.repository;

import lunna.school.model.SchoolLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:12 PM
 **/

public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, UUID> {
    @Query("SELECT sl FROM SchoolLevel sl WHERE sl.school_level_name=?1")
    SchoolLevel getByName(String school_level_name);
}
