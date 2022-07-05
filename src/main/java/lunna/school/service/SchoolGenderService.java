package lunna.school.service;

import lunna.school.model.SchoolGender;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:23 PM
 **/
public interface SchoolGenderService extends IService<SchoolGender> {
    SchoolGender getByName(String school_gender_name);
}
