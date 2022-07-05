package lunna.school.service;

import lunna.school.model.SubCounty;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:06 PM
 **/
public interface SubCountyService extends IService<SubCounty> {
    SubCounty getByName(String sub_county_name);
    List<SubCounty> getByCode(Long code);
    SubCounty getById(Long id);
}
