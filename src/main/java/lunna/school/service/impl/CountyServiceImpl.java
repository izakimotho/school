package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.County;
import lunna.school.repository.CountyRepository;
import lunna.school.service.CountyService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:54 PM
 **/
@Service
public class CountyServiceImpl implements CountyService {
    final
    CountyRepository countyRepository;

    public CountyServiceImpl(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }

    @Override
    public List<County> listAll() {
        return countyRepository.findAll();
    }

    @Override
    public County getById(UUID id) {
        return null;
    }


    @Override
    public County getById(Long id) {
        return countyRepository.getById(id);
    }

    @Override
    public County saveOrUpdate(County county) {
        return countyRepository.saveAndFlush(county);
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
    public void delete(County county) {
        try {
            countyRepository.delete(county);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public County getByName(String county_name) {
        return countyRepository.getByName(county_name);
    }
}
