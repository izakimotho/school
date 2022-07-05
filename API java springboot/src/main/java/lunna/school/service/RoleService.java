package lunna.school.service;

import lunna.school.model.Role;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 3:53 PM
 **/
public interface RoleService extends IService<Role> {
    Role getByName(String role_name);
    Role getByNameOrgId(String role_name, UUID org_id);
    List<Role> getByOrgId(UUID org_id);
}
