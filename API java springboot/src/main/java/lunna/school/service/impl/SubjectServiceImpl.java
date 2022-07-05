package lunna.school.service.impl;

import lunna.school.dto.SubjectDetailsDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.EducationSystem;
import lunna.school.model.SchoolLevel;
import lunna.school.model.Subject;
import lunna.school.repository.SubjectRepository;
import lunna.school.service.SubjectService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 11:07 AM
 **/
@Service
public class SubjectServiceImpl implements SubjectService {
    final
    SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> listAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getById(UUID id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Subject not found"));
    }


    @Override
    public Subject saveOrUpdate(Subject subject) {
        if (subject == null) {
            throw new RecordNotFoundException("Entity is empty");
        }
        return subjectRepository.saveAndFlush(subject);
    }

    @Override
    public Long count() {
        return subjectRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return null;
    }

    @Override
    public void delete(Subject subject) {
        try {
            subjectRepository.delete(subject);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Subject getByName(String subject_name) {
        return subjectRepository.getByName(subject_name);
    }


    @Override
    public List<Subject> filterSubjects(Subject subject) {
        EducationSystem educationSystem = subject.getEducation_system() == null ? new EducationSystem() : subject.getEducation_system();
        SchoolLevel schoolLevel = subject.getSchool_level() == null ? new SchoolLevel() : subject.getSchool_level();
        return subjectRepository.filterByEducationSystemAndSchoolLevel(educationSystem.getEducation_system_id()
                , schoolLevel.getSchool_level_id());
    }

    @Override
    public SubjectDetailsDto getSubjectDetails(String subject_id) {
        Subject subject = null;
        if (subject_id != null) {
            subject = subjectRepository.getById(UUID.fromString(subject_id));
        }

        return new SubjectDetailsDto();
    }

    @Override
    public List<Subject> getSubjectsBySchoolLevel(UUID school_level_id) {
        return subjectRepository.findAllBySchoolLevel(school_level_id);
    }

    @Override
    public List<Subject> getSubjectsByEducationSystem(UUID education_system_id) {
        return subjectRepository.findAllByEducationSystem(education_system_id);
    }

    @Override
    public void softDelete(Subject subject) {
        subjectRepository.saveAndFlush(subject);

    }
}
