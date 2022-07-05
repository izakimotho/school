package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SubCounty;
import lunna.school.repository.SubCountyRepository;
import lunna.school.service.SubCountyService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:55 PM
 **/
@Service
public class SubCountyServiceImpl implements SubCountyService {

    final SubCountyRepository subCountyRepository;

    public SubCountyServiceImpl(SubCountyRepository subCountyRepository) {
        this.subCountyRepository = subCountyRepository;
    }

    @Override
    public List<SubCounty> listAll() {
        return subCountyRepository.findAll();
    }

    @Override
    public SubCounty getById(Long id) {
        return subCountyRepository.getById(id);
    }

    @Override
    public SubCounty getById(UUID id) {
        return null;
    }

    @Override
    public List<SubCounty> getByCode(Long code) {
        return subCountyRepository.findByCountyCode(code);
    }

    @Override
    public SubCounty saveOrUpdate(SubCounty subCounty) {
        return subCountyRepository.save(subCounty);
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
    public void delete(SubCounty subCounty) {
        try {
            subCountyRepository.delete(subCounty);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SubCounty getByName(String sub_county_name) {
        return subCountyRepository.getByName(sub_county_name);
    }
}
