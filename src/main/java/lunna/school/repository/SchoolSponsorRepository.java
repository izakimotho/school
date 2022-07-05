package lunna.school.repository;

import lunna.school.model.SchoolSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:04 PM
 **/

public interface SchoolSponsorRepository extends JpaRepository<SchoolSponsor, UUID> {
    @Query("SELECT ss FROM SchoolSponsor ss WHERE ss.school_sponsor_name =?1")
    SchoolSponsor getByName(String school_sponsor_name);
}
