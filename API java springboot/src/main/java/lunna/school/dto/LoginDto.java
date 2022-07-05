package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 26. Jun 2021 6:25 PM
 **/
@Getter
@Setter
public class LoginDto {
    private String username;
    private String password;
    public LoginDto(){}

}
