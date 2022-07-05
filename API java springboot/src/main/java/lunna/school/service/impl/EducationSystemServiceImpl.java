package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.EducationSystem;
import lunna.school.repository.EducationSystemRepository;
import lunna.school.service.EducationSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 5:44 PM
 **/
@Service("EducationSystemService")
public class EducationSystemServiceImpl implements EducationSystemService {

    final EducationSystemRepository educationSystemRepository;

    @Autowired
    public EducationSystemServiceImpl(EducationSystemRepository educationSystemRepository) {
        this.educationSystemRepository = educationSystemRepository;
    }

    @Override
    public EducationSystem create(EducationSystem educationSystem) {
        return educationSystemRepository.saveAndFlush(educationSystem);
    }

    @Override
    public EducationSystem getById(UUID id) {
        return educationSystemRepository.findById(id).orElse(null);
    }

    @Override
    public List<EducationSystem> getList() {
        return educationSystemRepository.findAll();
    }

    @Override
    public EducationSystem update(EducationSystem educationSystem) {
        return educationSystemRepository.saveAndFlush(educationSystem);
    }

    @Override
    public void delete(EducationSystem educationSystem) {
        try {
            educationSystemRepository.delete(educationSystem);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }
}
