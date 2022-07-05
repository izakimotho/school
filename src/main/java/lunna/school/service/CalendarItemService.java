package lunna.school.service;

import lunna.school.model.CalendarItem;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 23. Jul 2021 11:56 AM
 **/
public interface CalendarItemService extends IService<CalendarItem> {
    List<CalendarItem> getListBySchoolId(UUID org_id);
    void softDelete(CalendarItem calendarItem);
}
