package lunna.school.repository;

import lunna.school.model.SchoolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:11 PM
 **/

public interface SchoolTypeRepository extends JpaRepository<SchoolType, UUID> {
    @Query("SELECT st FROM SchoolType st WHERE st.school_type_name=?1")
    SchoolType getByName(String school_type_name);
}
