package lunna.school.service;

import lunna.school.model.SchoolLevel;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:13 PM
 **/
public interface SchoolLevelService extends IService<SchoolLevel> {
    SchoolLevel getByName(String school_level_name);
}
