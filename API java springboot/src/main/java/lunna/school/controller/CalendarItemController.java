package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.CalendarItem;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.CalendarItemService;
import lunna.school.service.IService;
import lunna.school.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 10:47 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CalendarItemController {
    final
    CalendarItemService calendarItemIService;
    @Autowired
    JwtUtils utils;

    public CalendarItemController(CalendarItemService calendarItemIService) {
        this.calendarItemIService = calendarItemIService;
    }

    @PostAuthorize("hasAuthority(\"can_add_calendar_items\")")
    @PostMapping("/calendar_item/create")
    public ApiResponse create(@Valid @RequestBody CalendarItem calendarItem) throws RecordNotFoundException {
        CalendarItem calendarItem1 = null;
        try {
            calendarItem1 = calendarItemIService.saveOrUpdate(calendarItem);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                calendarItem1,
                "Calendar created successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_calendar_items\")")
    @GetMapping("/calendar_items")
    public ApiResponse getCalendarItems(HttpServletRequest request) throws RecordNotFoundException {
        List<CalendarItem> calendarItems = null;
        if (request.isUserInRole("ROLE_Super Admin")) {
            calendarItems = calendarItemIService.listAll();
        } else {
            calendarItems = calendarItemIService.getListBySchoolId(utils.getSchoolId(request));
        }

        return new ApiResponse(
                calendarItems,
                "Calender list",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_calendar_items\")")
    @PutMapping("/calendar_item/update")
    public ApiResponse update(@Valid @RequestBody CalendarItem calendarItem) throws RecordNotFoundException {
        CalendarItem calendarItem1 = null;
        try {
            calendarItem1 = calendarItemIService.saveOrUpdate(calendarItem);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                calendarItem1,
                "Calendar updated successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_calendar_items\")")
    @DeleteMapping("/calendar_item/{id}/delete")
    public ApiResponse getCalendarItems(@PathVariable String id) throws RecordNotFoundException {
        CalendarItem calendarItem = calendarItemIService.getById(UUID.fromString(id));
        if (calendarItem == null) {
            throw new BadRequestException("Calendar Item Not found");
        }
        try {
            calendarItemIService.delete(calendarItem);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                null,
                "Delete successfully",
                HttpStatus.OK.value()
        );
    }

}
