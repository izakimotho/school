package lunna.school.repository;

import lunna.school.model.CalendarItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 10:44 AM
 **/

public interface CalendarItemRepository extends JpaRepository<CalendarItem, UUID> {
    List<CalendarItem> findAllByDeletedAtIsNull();
    @Query(value = "SELECT ci FROM CalendarItem ci WHERE ci.deletedAt is NULL")
    List<CalendarItem> findAllByOrgId(UUID org_id);
}
