package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Grades;
import lunna.school.repository.GradesRepository;
import lunna.school.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * GradesService
 *
 * @author Collins K. Sang
 * 2021/07/07 14:16
 **/
@Service
public class GradesServiceImpl implements GradesService {
    final GradesRepository gradesRepository;

    @Autowired
    public GradesServiceImpl(GradesRepository gradesRepository) {
        this.gradesRepository = gradesRepository;
    }

    @Override
    public List<Grades> listAll() {
        return gradesRepository.findAll();
    }


    @Override
    public Grades getById(UUID id) {
        return gradesRepository.getById(id);
    }


    @Override
    public Grades saveOrUpdate(Grades grades) {
        return gradesRepository.save(grades);
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
    public void delete(Grades grades) {
        try {
            gradesRepository.delete(grades);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Grades getByName(String grade_name) {
        return gradesRepository.getByName(grade_name);
    }

    public List<Grades> getPerSchool(UUID school_id) {
        return gradesRepository.getBySchool_id(school_id);
    }
}
