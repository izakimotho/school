package lunna.school.service;

import lunna.school.model.Permission;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 19. Jul 2021 11:05 AM
 **/

public interface PermissionService extends IService<Permission>{
    void softDelete(Permission permission);
    Permission getById(Long id);
    List<Permission> getByUserLevel(Long level);
}
