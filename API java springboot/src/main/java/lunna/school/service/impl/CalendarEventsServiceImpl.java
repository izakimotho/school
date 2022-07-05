package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.CalendarEvents;
import lunna.school.repository.CalendarEventsRepository;
import lunna.school.service.CalendarEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 11:29 AM
 **/
@Service
public class CalendarEventsServiceImpl implements CalendarEventsService {

    final CalendarEventsRepository calendarEventsRepository;

    @Autowired
    public CalendarEventsServiceImpl(CalendarEventsRepository calendarEventsRepository) {
        this.calendarEventsRepository = calendarEventsRepository;
    }

    @Override
    public List<CalendarEvents> listAll() {
        return calendarEventsRepository.findAll();
    }


    @Override
    public CalendarEvents getById(UUID id) {
        return calendarEventsRepository.getById(id);
    }

    @Override
    public CalendarEvents saveOrUpdate(CalendarEvents calendarEvents) {
        return calendarEventsRepository.saveAndFlush(calendarEvents);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Long count(UUID id) {
        return null;
    }

    @Override
    public void delete(CalendarEvents calendarEvents) {
        try {
            calendarEventsRepository.delete(calendarEvents);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<CalendarEvents> getEventPerSchoolId(UUID school_id) {
        return calendarEventsRepository.findAllBySchoolId(school_id);
    }
}
