package lunna.school.repository;

import lunna.school.model.Role;
import lunna.school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 4:00 PM
 **/

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.role_name =?1")
    Role findByRole_name(String role);
    @Query("SELECT r FROM Role r WHERE r.role_name =?1 AND r.organization.org_id =?2")
    Role findByRoleNameOrgId(String role, UUID org_id);
    @Query("SELECT r FROM Role r WHERE r.organization.org_id =?1")
    List<Role> findByOrgId(UUID org_id);
    @Query("SELECT r FROM Role r WHERE r.role_id =?1")
    Role findByUUID(UUID id);
    @Query("SELECT r FROM Role r WHERE r.role_id in(?1)")
    List<Role> findByRole_idIn(Set<UUID> ids);

    @Query(nativeQuery = true, value = "DELETE  FROM user_roles where user_id=?1")
    void deleteUserRole(UUID user_id);

}
