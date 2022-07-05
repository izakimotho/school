package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 8:41 AM
 **/
@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String active;

    private List<RolesDto> roles;

    public UserDto() {
    }
}
