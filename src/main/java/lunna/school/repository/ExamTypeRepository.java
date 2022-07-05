package lunna.school.repository;

import lunna.school.model.ExamType;
import lunna.school.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 4:29 PM
 **/

public interface ExamTypeRepository extends JpaRepository<ExamType, UUID> {

    List<ExamType> findAllByDeletedAtIsNull();
    List<ExamType> findAllByOrganizationAndDeletedAtIsNull(Organization organization);
}
