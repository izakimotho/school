package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Ward;
import lunna.school.repository.WardRepository;
import lunna.school.service.WardService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:56 PM
 **/

@Service
public class WardServiceImpl implements WardService {

    final WardRepository wardRepository;

    public WardServiceImpl(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    @Override
    public List<Ward> listAll() {
        return wardRepository.findAll();
    }

    @Override
    public Ward getById(Long id) {
        return wardRepository.getById(id);
    }

    @Override
    public Ward getById(UUID id) {
        return null;
    }

    @Override
    public List<Ward> getByCode(Long code) {
        return wardRepository.getBySubCountyCode(code);
    }

    @Override
    public Ward saveOrUpdate(Ward ward) {
        return wardRepository.save(ward);
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
    public void delete(Ward ward) {
        try {
            wardRepository.delete(ward);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

    }

    @Override
    public Ward getByName(String ward_name) {
        return wardRepository.getByName(ward_name);
    }
}
