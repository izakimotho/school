package lunna.school.service;

import lunna.school.model.UserLevel;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:11 PM
 **/
public interface UserLevelService extends IService<UserLevel> {
    UserLevel getById(Integer id);
    UserLevel getByName(String category_name);

}
