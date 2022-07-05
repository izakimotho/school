package lunna.school.repository;

import lunna.school.model.ExamSchedule;
import lunna.school.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 7:45 AM
 **/

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, UUID> {

    List<ExamSchedule> findAllByDeletedAtIsNull();

    List<ExamSchedule> findAllByOrganizationAndDeletedAtIsNull(Organization organization);

    @Query(value = "SELECT e FROM ExamSchedule e WHERE e.class_model.class_id =?1 AND e.organization.org_id =?2")
    List<ExamSchedule> fetchByClassOrg(UUID school_stream_id,UUID org_id);
}
