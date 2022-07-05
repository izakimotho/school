package lunna.school.repository;

import lunna.school.model.Organization;
import lunna.school.model.UserTypeSpecific;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * UserTypeSpecificRepository
 *
 * @author Collins K. Sang
 * 2021/07/13 15:28
 **/
public interface UserTypeSpecificRepository extends JpaRepository<UserTypeSpecific, UUID> {
    @Query("SELECT ut FROM UserTypeSpecific ut WHERE ut.type_name=?1")
    UserTypeSpecific findByName(String type_name);

    List<UserTypeSpecific> findAllByDeletedAtIsNull();
    List<UserTypeSpecific> findByOrganization(Organization organization);
}
