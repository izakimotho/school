package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.CalendarItem;
import lunna.school.repository.CalendarItemRepository;
import lunna.school.service.CalendarItemService;
import lunna.school.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 10:45 AM
 **/
@Service
public class CalendarItemServiceImpl implements CalendarItemService {

    final CalendarItemRepository calendarItemRepository;

    @Autowired
    public CalendarItemServiceImpl(CalendarItemRepository calendarItemRepository) {
        this.calendarItemRepository = calendarItemRepository;
    }

    @Override
    public List<CalendarItem> listAll() {
        return calendarItemRepository.findAllByDeletedAtIsNull();
    }


    @Override
    public CalendarItem getById(UUID id) {
        return calendarItemRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Calendar Item not found: id" + id));
    }


    @Override
    public CalendarItem saveOrUpdate(CalendarItem calendarItem) {
        return calendarItemRepository.saveAndFlush(calendarItem);
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
    public void delete(CalendarItem calendarItem) {
        try {
            calendarItemRepository.delete(calendarItem);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<CalendarItem> getListBySchoolId(UUID org_id) {
        return calendarItemRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public void softDelete(CalendarItem calendarItem) {
        calendarItem.setDeletedAt(new Date());
        calendarItemRepository.save(calendarItem);

    }
}
