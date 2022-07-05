package lunna.school.service.impl;

import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.AcademicYear;
import lunna.school.repository.AcademicYearRepository;
import lunna.school.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
@Service
public class AcademicYearServiceImpl implements AcademicYearService {
    @Autowired
    AcademicYearRepository academicYearRepository;

    @Override
    public List<AcademicYear> listAll() {
        return academicYearRepository.findAll();
    }

    @Override
    public AcademicYear getById(UUID id) {
        return academicYearRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Academic Year NOT FOUND: "+ id));
    }

    @Override
    public AcademicYear saveOrUpdate(AcademicYear academicYear) {
        return academicYearRepository.saveAndFlush(academicYear);
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
    public void delete(AcademicYear academicYear) {
        academicYearRepository.delete(academicYear);
    }

    @Override
    public List<AcademicYear> getAcademicYearBySchool(UUID school_id) {
        return academicYearRepository.findAllBySchoolId(school_id);
    }

    @Override
    public AcademicYear getActiveAcademicYearBySchool(UUID school_id) {
        return academicYearRepository.findActiveBySchoolId(school_id);

    }
}
