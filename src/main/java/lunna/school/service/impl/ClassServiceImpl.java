package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ClassModel;
import lunna.school.model.EducationSystem;
import lunna.school.model.SchoolLevel;
import lunna.school.repository.ClassesRepository;
import lunna.school.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * ClassServiceImpl
 *
 * @author Collins K. Sang
 * 2021/07/02 13:15
 **/
@Service
public class ClassServiceImpl implements ClassService {
    final ClassesRepository classesRepository;

    @Autowired
    public ClassServiceImpl(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    @Override
    public List<ClassModel> listAll() {
        return classesRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public ClassModel getById(UUID id) {
        return classesRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Class not found"));
    }

    @Override
    public ClassModel saveOrUpdate(ClassModel classModel) {
        return classesRepository.save(classModel);
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
    public void delete(ClassModel classModel) {
        try {
            classesRepository.delete(classModel);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public ClassModel getByName(String class_name) {
        return classesRepository.getByName(class_name);
    }

    @Override
    public List<ClassModel> getByEducationAndSchoolLevel(UUID education_system, UUID school_level) {
        return classesRepository.filterByEducationSystemAndSchoolLevel(education_system, school_level);
    }

    @Override
    public List<ClassModel> filterClasses(ClassModel classModel) {
        EducationSystem educationSystem = classModel.getEducation_system() == null ? new EducationSystem() : classModel.getEducation_system();
        SchoolLevel schoolLevel = classModel.getSchool_level() == null ? new SchoolLevel() : classModel.getSchool_level();
        return classesRepository.filterByEducationSystemAndSchoolLevel(educationSystem.getEducation_system_id()
                , schoolLevel.getSchool_level_id());
    }
}
