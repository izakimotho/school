package lunna.school.repository;

import lunna.school.model.SchoolGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:10 PM
 **/

public interface SchoolGenderRepository extends JpaRepository<SchoolGender, UUID> {
    @Query("SELECT sg FROM SchoolGender sg WHERE sg.school_gender_name = ?1")
    SchoolGender getByName(String school_gender_name);
}
