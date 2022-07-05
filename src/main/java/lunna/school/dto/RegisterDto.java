package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Organization;
import lunna.school.model.UserLevel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 26. Jun 2021 7:28 PM
 **/
@Getter
@Setter
public class RegisterDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<UUID> role;

    private UserLevel user_level;

    private String avatar;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotEmpty(message = "*Please provide your name")
    private String firstName;

    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    private Organization organization;


}
