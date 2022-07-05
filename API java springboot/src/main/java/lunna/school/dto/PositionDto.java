package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Organization;
import lunna.school.model.User;
import lunna.school.model.UserType;

import java.util.Date;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 4/26/22, Tuesday
 **/
@Getter
@Setter
public class PositionDto {
    private UUID position_id;
    private String position_name;
    private UserType position_holder;
    private String description;
    private SchoolDto organization;
    private UserDto created_by;
    private Date createdAt;

    public PositionDto() {
    }
}
