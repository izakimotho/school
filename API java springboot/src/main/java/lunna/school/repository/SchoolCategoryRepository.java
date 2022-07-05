package lunna.school.repository;

import lunna.school.model.SchoolCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:29 PM
 **/

public interface SchoolCategoryRepository extends JpaRepository<SchoolCategory, UUID> {
    @Query("SELECT sc FROM SchoolCategory  sc WHERE sc.school_category_name = ?1")
    SchoolCategory getByName(String school_category_name);
}
