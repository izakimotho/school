package lunna.school.service;

import lunna.school.model.UserTypeSpecific;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 9:43 AM
 **/
public interface UserTypeSpecificService extends IService<UserTypeSpecific> {
    UserTypeSpecific getByName(String type_name);
}
