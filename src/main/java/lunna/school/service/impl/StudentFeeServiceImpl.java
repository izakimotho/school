package lunna.school.service.impl;

import lunna.school.dto.FeeRecordDto;
import lunna.school.model.StudentFee;
import lunna.school.model.Terms;
import lunna.school.repository.StudentFeeRepository;
import lunna.school.service.FeeService;
import lunna.school.service.StudentFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/16/22 9:56 AM
 * school
 * StudentFeeServiceImpl
 * IntelliJ IDEA
 **/
@Service
public class StudentFeeServiceImpl implements StudentFeeService {
    final StudentFeeRepository studentFeeRepository;
    final FeeService feeService;

    @Autowired
    public StudentFeeServiceImpl(StudentFeeRepository studentFeeRepository, FeeService feeService) {
        this.studentFeeRepository = studentFeeRepository;
        this.feeService = feeService;
    }

    @Override
    public List<StudentFee> listAll() {
        return null;
    }

    @Override
    public StudentFee getById(UUID id) {
        return null;
    }

    @Override
    public StudentFee saveOrUpdate(StudentFee studentFee) {
        return studentFeeRepository.save(studentFee);
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
    public void delete(StudentFee studentFee) {
        studentFeeRepository.delete(studentFee);

    }

    @Override
    public List<FeeRecordDto> ListPerSchool(UUID org_id) {
        return studentFeeRepository.findByOrg_id(org_id);
    }

    @Override
    public List<StudentFee> getFeeByStudent(UUID student_id) {
        return studentFeeRepository.getByStudent_id(student_id);
    }

    @Override
    public List<FeeRecordDto> getFeeByClass(UUID class_id) {
        return studentFeeRepository.getFeeByClass(class_id);
    }

    @Override
    public List<FeeRecordDto> getFeeByTermBySchool(UUID terms, UUID school_id) {
        return studentFeeRepository.getFeeByTermBySchool(terms, school_id);
    }

    @Override
    public List<FeeRecordDto> getFeeByTermByClass(UUID terms, UUID class_id) {
        return studentFeeRepository.getFeeByTermByClass(terms, class_id);

    }

    @Override
    public List<StudentFee> getFeeByTermByStudent(UUID terms, UUID student_id) {
        return studentFeeRepository.getFeeByTermByStudent(terms, student_id);
    }

    @Override
    public Float getFeeSumByTermByStudent(UUID terms, UUID student_id) {
        return studentFeeRepository.getFeeSumByTermByStudent(terms, student_id);
    }

    @Override
    public void deleteByStudent(List<StudentFee> studentFee) {
        studentFeeRepository.deleteAll(studentFee);
    }

    @Override
    public void deleteByClass(UUID class_id) {
            studentFeeRepository.deleteByClass(class_id);

    }

    @Override
    public void deleteBySchool(UUID school_id) {
        studentFeeRepository.deleteBySchool(school_id);
    }

}
