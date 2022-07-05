package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.GenderEnum;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
@Getter
@Setter
public class ProfileDto  {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone_number;
    private String avatar;
    private GenderEnum gender;
    private SchoolDto organization;

    public ProfileDto(){}
}
