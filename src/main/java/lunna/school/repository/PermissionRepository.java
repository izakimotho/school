package lunna.school.repository;

import lunna.school.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 19. Jul 2021 11:04 AM
 **/

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByDeletedAtIsNull();
    @Query(value = "SELECT p FROM Permission p WHERE p.user_level = ?1 ORDER BY p.createdAt DESC")
    List<Permission> findAllByUserLevel(Long level);

}
