package lunna.school.service;

import lunna.school.model.UserType;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
public interface UserTypeService extends IService<UserType> {
    UserType getById(Long id);
}
