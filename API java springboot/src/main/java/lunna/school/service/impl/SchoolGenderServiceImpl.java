package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolGender;
import lunna.school.repository.SchoolGenderRepository;
import lunna.school.service.SchoolGenderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:33 PM
 **/
@Service
public class SchoolGenderServiceImpl implements SchoolGenderService {
    final
    SchoolGenderRepository schoolGenderRepository;

    public SchoolGenderServiceImpl(SchoolGenderRepository schoolGenderRepository) {
        this.schoolGenderRepository = schoolGenderRepository;
    }

    @Override
    public List<SchoolGender> listAll() {
        return schoolGenderRepository.findAll();
    }

    @Override
    public SchoolGender getById(UUID id) {
        return schoolGenderRepository.getById(id);
    }


    @Override
    public SchoolGender saveOrUpdate(SchoolGender schoolGender) {
        return schoolGenderRepository.saveAndFlush(schoolGender);
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
    public void delete(SchoolGender schoolGender) {
        try {
            schoolGenderRepository.delete(schoolGender);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolGender getByName(String school_gender_name) {
        return schoolGenderRepository.getByName(school_gender_name);
    }
}
