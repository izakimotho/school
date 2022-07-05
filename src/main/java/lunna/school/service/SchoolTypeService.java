package lunna.school.service;

import lunna.school.model.SchoolType;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:09 PM
 **/
public interface SchoolTypeService extends IService<SchoolType> {
    SchoolType getByName(String school_type_name);
}
