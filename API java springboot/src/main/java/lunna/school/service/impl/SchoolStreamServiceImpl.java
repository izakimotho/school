package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.SchoolStream;
import lunna.school.repository.SchoolStreamRepository;
import lunna.school.service.SchoolStreamService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 05. Jul 2021 9:05 AM
 **/
@Service
public class SchoolStreamServiceImpl implements SchoolStreamService {
    final
    SchoolStreamRepository schoolStreamRepository;

    public SchoolStreamServiceImpl(SchoolStreamRepository schoolStreamRepository) {
        this.schoolStreamRepository = schoolStreamRepository;
    }

    @Override
    public List<SchoolStream> listAll() {
        return schoolStreamRepository.findAllByDeletedAtIsNull();
    }


    @Override
    public SchoolStream getById(UUID id) {
        return schoolStreamRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Stream not found"));
    }

    @Override
    public SchoolStream saveOrUpdate(SchoolStream schoolStream) {
        return schoolStreamRepository.saveAndFlush(schoolStream);
    }

    @Override
    public Long count() {
        return schoolStreamRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return schoolStreamRepository.count(id);
    }

    @Override
    public void delete(SchoolStream schoolStream) {
        try {
            schoolStreamRepository.delete(schoolStream);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolStream getByName(String stream_name) {
        return schoolStreamRepository.getByName(stream_name);
    }

    @Override
    public List<SchoolStream> listBySchoolId(UUID school_id) {
        return schoolStreamRepository.findSchoolStreamBySchoolId(school_id);
    }

    @Override
    public SchoolStream schoolStreamUpdate(SchoolStream schoolStream) {
        try {
            return schoolStreamRepository.saveAndFlush(schoolStream);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void softDelete(SchoolStream schoolStream) {
        schoolStream.setDeletedAt(new Date());
        schoolStreamRepository.save(schoolStream);
    }
}
