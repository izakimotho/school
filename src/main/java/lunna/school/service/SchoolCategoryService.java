package lunna.school.service;

import lunna.school.model.SchoolCategory;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:20 PM
 **/
public interface SchoolCategoryService extends IService<SchoolCategory> {
    SchoolCategory getByName(String school_category_name);
}
