package lunna.school.service;

import lunna.school.model.CalendarEvents;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/9/22, Monday
 **/
public interface CalendarEventsService extends IService<CalendarEvents> {
    List<CalendarEvents> getEventPerSchoolId(UUID school_id);
}
