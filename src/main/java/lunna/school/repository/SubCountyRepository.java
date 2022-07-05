package lunna.school.repository;

import lunna.school.model.SubCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:53 PM
 **/

public interface SubCountyRepository extends JpaRepository<SubCounty, Long> {
    @Query("SELECT sc FROM SubCounty  sc WHERE sc.county_code = ?1")
    List<SubCounty> findByCountyCode(Long code);

    @Query("SELECT sc FROM SubCounty sc WHERE sc.sub_county_name=?1")
    SubCounty getByName(String sub_county_name);
}
