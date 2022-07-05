package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolLevel;
import lunna.school.repository.SchoolLevelRepository;
import lunna.school.service.SchoolLevelService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:32 PM
 **/
@Service
public class SchoolLevelServiceImpl implements SchoolLevelService {
    final
    SchoolLevelRepository schoolLevelRepository;

    public SchoolLevelServiceImpl(SchoolLevelRepository schoolLevelRepository) {
        this.schoolLevelRepository = schoolLevelRepository;
    }

    @Override
    public List<SchoolLevel> listAll() {
        return schoolLevelRepository.findAll();
    }

    @Override
    public SchoolLevel getById(UUID id) {
        return schoolLevelRepository.getById(id);
    }


    @Override
    public SchoolLevel saveOrUpdate(SchoolLevel schoolLevel) {
        return schoolLevelRepository.saveAndFlush(schoolLevel);
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
    public void delete(SchoolLevel schoolLevel) {
        try {
            schoolLevelRepository.delete(schoolLevel);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolLevel getByName(String school_level_name) {
        return schoolLevelRepository.getByName(school_level_name);
    }
}
