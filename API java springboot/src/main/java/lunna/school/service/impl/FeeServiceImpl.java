package lunna.school.service.impl;

import lunna.school.model.ClassModel;
import lunna.school.model.Fee;
import lunna.school.model.Terms;
import lunna.school.repository.ClassesRepository;
import lunna.school.repository.FeeRepository;
import lunna.school.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/13/22 11:40 AM
 * school
 * FeeServiceImpl
 * IntelliJ IDEA
 **/
@Service
public class FeeServiceImpl implements FeeService {
    final FeeRepository feeRepository;
    final ClassesRepository classesRepository;

    @Autowired
    public FeeServiceImpl(FeeRepository feeRepository, ClassesRepository classesRepository) {
        this.feeRepository = feeRepository;
        this.classesRepository = classesRepository;
    }

    @Override
    public List<Fee> listAll() {
        return feeRepository.findAll();
    }



    @Override
    public Fee getById(UUID id) {
        return feeRepository.getById(id);
    }

    @Override
    public Fee saveOrUpdate(Fee fee) {
        return feeRepository.save(fee);
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
    public void delete(Fee fee) {
        feeRepository.delete(fee);
    }

    @Override
    public List<Fee> ListPerClass(UUID class_id) {
        ClassModel classModel = classesRepository.getById(class_id);
        return feeRepository.findByClass_id(classModel.getClass_id());
    }

    @Override
    public List<Fee> ListPerTerm(Terms term) {
        return feeRepository.findByTerms(term);
    }

    @Override
    public List<Fee> ListPerSchool(UUID school_id) {
        return feeRepository.findBySchool(school_id);
    }

    @Override
    public List<Fee> ListPerTermPerSchool(Terms term, UUID school_id) {
        return feeRepository.findByTermsBySchool(term, school_id);
    }

    @Override
    public Fee getFeeByClassByTerm(UUID class_id, Terms terms) {
        return feeRepository.getFeeByClassByTerm(class_id, terms);
    }
}
