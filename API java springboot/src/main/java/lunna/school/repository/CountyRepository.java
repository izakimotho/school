package lunna.school.repository;

import lunna.school.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:51 PM
 **/

public interface CountyRepository extends JpaRepository<County, Long> {

    @Query("SELECT c FROM County c WHERE c.county_name=?1")
    County getByName(String county_name);
}
