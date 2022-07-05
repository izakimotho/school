package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Role;
import lunna.school.model.UserLevel;

import java.util.Set;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 8:41 AM
 **/
@Getter
@Setter
public class UserDtoExt {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String active;
    private UserLevel userLevel;
    private String photo_url;
    private Set<Role> roles;

    public UserDtoExt() {
    }
}
