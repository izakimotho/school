package lunna.school.service;

import lunna.school.model.County;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 3:55 PM
 **/
public interface CountyService extends IService<County> {
    County getByName(String county_name);
    County getById(Long id);

}
