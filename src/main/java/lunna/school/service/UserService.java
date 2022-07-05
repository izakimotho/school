package lunna.school.service;

import lunna.school.dto.UserDtoExt;
import lunna.school.model.User;
import lunna.school.model.UserType;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 8:28 PM
 **/

public interface UserService {
    User findUserByEmail(String email);
    List<UserDtoExt> getAllUsers();
    User findByUsername(String username);
    User saveUser(User user);
    User updateUser(User user);
    Boolean userExist(String username);
    void deleteUser(User user);
    List<UserType> getUserTypes();
    User profile(String username);
    Long count();
    Long count(UUID id);


}
