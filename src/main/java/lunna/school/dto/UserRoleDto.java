package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Role;
import lunna.school.model.User;

/**
 * IntelliJ IDEA
 * school
 * UserRoleDto
 *
 * @author Collins K. Sang
 * 2021/07/07 12:00
 **/
@Getter
@Setter
@ToString
public class UserRoleDto {
    private User user;
    private Role role;
}
