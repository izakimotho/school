package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolCategory;
import lunna.school.repository.SchoolCategoryRepository;
import lunna.school.service.SchoolCategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:34 PM
 **/
@Service
public class SchoolCategoryServiceImpl implements SchoolCategoryService {
    final
    SchoolCategoryRepository schoolCategoryRepository;

    public SchoolCategoryServiceImpl(SchoolCategoryRepository schoolCategoryRepository) {
        this.schoolCategoryRepository = schoolCategoryRepository;
    }

    @Override
    public List<SchoolCategory> listAll() {

        return schoolCategoryRepository.findAll();
    }

    @Override
    public SchoolCategory getById(UUID id) {
        return schoolCategoryRepository.getById(id);
    }

    @Override
    public SchoolCategory saveOrUpdate(SchoolCategory schoolCategory) {
        return schoolCategoryRepository.saveAndFlush(schoolCategory);
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
    public void delete(SchoolCategory schoolCategory) {
        try {
            schoolCategoryRepository.delete(schoolCategory);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolCategory getByName(String school_category_name) {
        return schoolCategoryRepository.getByName(school_category_name);
    }
}
