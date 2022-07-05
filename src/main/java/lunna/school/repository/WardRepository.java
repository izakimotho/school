package lunna.school.repository;

import lunna.school.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:53 PM
 **/

public interface WardRepository extends JpaRepository<Ward, Long> {
    @Query("SELECT w FROM Ward w WHERE w.sub_county_code=?1")
    List<Ward> getBySubCountyCode(Long code);

    @Query("SELECT w FROM Ward w WHERE w.ward_name=?1")
    Ward getByName(String ward_name);
}
