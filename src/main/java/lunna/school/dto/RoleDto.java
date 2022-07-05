package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Permission;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/10/22, Tuesday
 **/
@Getter
@Setter
public class RoleDto {

    private UUID role_id;
    private String role_name;
    private String description;
    private List<Permission> permissions;

}
