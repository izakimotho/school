package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolType;
import lunna.school.repository.SchoolTypeRepository;
import lunna.school.service.SchoolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SchoolTypeServiceImpl implements SchoolTypeService {
    final SchoolTypeRepository schoolTypeRepository;

    @Autowired
    public SchoolTypeServiceImpl(SchoolTypeRepository schoolTypeRepository) {
        this.schoolTypeRepository = schoolTypeRepository;
    }

    @Override
    public List<SchoolType> listAll() {
        return schoolTypeRepository.findAll();
    }

    @Override
    public SchoolType getById(UUID id) {
        return schoolTypeRepository.getById(id);
    }


    @Override
    public SchoolType saveOrUpdate(SchoolType schoolType) {
        return schoolTypeRepository.save(schoolType);
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
    public void delete(SchoolType schoolType) {
        try {
            schoolTypeRepository.delete(schoolType);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolType getByName(String school_type_name) {
        return schoolTypeRepository.getByName(school_type_name);
    }

}
