package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.model.CalendarEvents;
import lunna.school.model.User;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.CalendarEventsService;
import lunna.school.service.IService;
import lunna.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 11:30 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CalendarEventsController {
    final CalendarEventsService calendarEventsIService;

    final UserService userService;
    @Autowired
    JwtUtils utils;

    @Autowired
    public CalendarEventsController(CalendarEventsService calendarEventsIService, UserService userService) {
        this.calendarEventsIService = calendarEventsIService;
        this.userService = userService;
    }

    @PostAuthorize("hasAuthority(\"can_add_calender_event\")")
    @PostMapping("/calendar_events/create")
    public ApiResponse create(@Valid @RequestBody CalendarEvents calendarEvents,
                              Principal principal) {
        User user = userService.findByUsername(principal.getName());
        CalendarEvents calendarEvents1 = null;
        if (calendarEvents.getOrganization() == null) {
            calendarEvents.setOrganization(user.getOrganization());
        }
        try {
            calendarEvents1 = calendarEventsIService.saveOrUpdate(calendarEvents);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                calendarEvents1,
                "Calendar Event created successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_calender_event\")")
    @GetMapping("/calendar_events")
    public ApiResponse getEventsList(HttpServletRequest request) {

        return new ApiResponse(
                calendarEventsIService.getEventPerSchoolId(utils.getSchoolId(request)),
                "Calendar Event Lists",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_calender_event\")")
    @PutMapping("/calendar_events/update")
    public ApiResponse update(@Valid @RequestBody CalendarEvents calendarEvents) {
        return new ApiResponse(
                calendarEventsIService.saveOrUpdate(calendarEvents),
                "Calendar Event updated successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_calender_event\")")
    @DeleteMapping("/calendar_events/{id}/delete")
    public ApiResponse delete(@PathVariable String id) throws Exception {
        CalendarEvents calendarEvents = calendarEventsIService.getById(UUID.fromString(id));
        try {
            calendarEventsIService.delete(calendarEvents);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                null,
                "Calendar Event delete successfully",
                HttpStatus.OK.value()
        );
    }
}
