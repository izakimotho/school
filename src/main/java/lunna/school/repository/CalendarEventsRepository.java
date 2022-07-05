package lunna.school.repository;

import lunna.school.model.CalendarEvents;
import lunna.school.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 11:28 AM
 **/

public interface CalendarEventsRepository extends JpaRepository<CalendarEvents, UUID> {

    @Query("SELECT e FROM CalendarEvents e WHERE e.organization.org_id = ?1")
    List<CalendarEvents> findAllBySchoolId(UUID id);
}
