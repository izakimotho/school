package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolSponsor;
import lunna.school.repository.SchoolSponsorRepository;
import lunna.school.service.SchoolSponsorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:01 PM
 **/
@Service
public class SchoolSponsorServiceImpl implements SchoolSponsorService {
    final
    SchoolSponsorRepository schoolSponsorRepository;

    public SchoolSponsorServiceImpl(SchoolSponsorRepository schoolSponsorRepository) {
        this.schoolSponsorRepository = schoolSponsorRepository;
    }

    @Override
    public List<SchoolSponsor> listAll() {
        return schoolSponsorRepository.findAll();
    }

    @Override
    public SchoolSponsor getById(UUID id) {
        return schoolSponsorRepository.getById(id);
    }


    @Override
    public SchoolSponsor saveOrUpdate(SchoolSponsor schoolSponsor) {
        return schoolSponsorRepository.saveAndFlush(schoolSponsor);
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
    public void delete(SchoolSponsor schoolSponsor) {

        try {
            schoolSponsorRepository.delete(schoolSponsor);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolSponsor getByName(String school_sponsor_name) {
        return schoolSponsorRepository.getByName(school_sponsor_name);
    }
}
