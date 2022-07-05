package lunna.school.service.impl;

import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.SchoolSubject;
import lunna.school.repository.SchoolSubjectRepository;
import lunna.school.service.SchoolSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/16/22, Monday
 **/
@Service
public class SchoolSubjectServiceImpl implements SchoolSubjectService {
    @Autowired
    SchoolSubjectRepository schoolSubjectRepository;

    @Override
    public List<SchoolSubject> listAll() {
        return schoolSubjectRepository.findAll();
    }

    @Override
    public SchoolSubject getById(UUID id) {
        return schoolSubjectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("School Subject NOT FOUND"));
    }

    @Override
    public SchoolSubject saveOrUpdate(SchoolSubject schoolSubject) {
        return schoolSubjectRepository.saveAndFlush(schoolSubject);
    }

    @Override
    public Long count() {
        return schoolSubjectRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return schoolSubjectRepository.count(id);
    }

    @Override
    public void delete(SchoolSubject schoolSubject) {
        schoolSubjectRepository.delete(schoolSubject);
    }

    @Override
    public List<SchoolSubject> getSubjectBYSchool(UUID orgId) {
        return schoolSubjectRepository.findSubjectBySchool(orgId);
    }
}
