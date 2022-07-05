package lunna.school.service.impl;

import lunna.school.dto.ExamsDto;
import lunna.school.dto.GradePostingDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ExamSchedule;
import lunna.school.model.GradePosting;
import lunna.school.model.Organization;
import lunna.school.model.Student;
import lunna.school.repository.GradePostingRepository;
import lunna.school.service.GradePostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 10:57 AM
 **/
@Service
public class GradePostingServiceImpl implements GradePostingService {
    final
    GradePostingRepository gradePostingRepository;

    @Autowired
    public GradePostingServiceImpl(GradePostingRepository gradePostingRepository) {
        this.gradePostingRepository = gradePostingRepository;
    }

    @Override
    public List<GradePosting> listAll() {
        return gradePostingRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public GradePosting getById(UUID id) {
        return gradePostingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Grade Posting not found"));
    }

    @Override
    public GradePosting saveOrUpdate(GradePosting gradePosting) {
        return gradePostingRepository.saveAndFlush(gradePosting);
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
    public void delete(GradePosting gradePosting) {
        try {
            gradePostingRepository.delete(gradePosting);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }


    }

    @Override
    public List<GradePostingDto> listByOrg(Organization organization) {
        List<GradePosting> gradePostingList = gradePostingRepository.findAllByDeletedAtIsNullAndOrganization(organization);
        ModelMapper modelMapper = new ModelMapper();
        List<GradePostingDto> gradePostingDtos = gradePostingList.stream().map(
                g -> modelMapper.map(g, GradePostingDto.class)
        ).collect(Collectors.toList());

       return gradePostingDtos;
    }

    @Override
    public void softDelete(GradePosting gradePosting) {
        gradePosting.setDeletedAt(new Date());
        gradePostingRepository.save(gradePosting);
    }

    @Override
    public List<GradePosting> listByExamSchedule(ExamSchedule examSchedule) {
        return gradePostingRepository.findAllByDeletedAtIsNullAndExamSchedule(examSchedule);
    }

    @Override
    public List<GradePosting> studentExams(Student student) {
        return gradePostingRepository.findAllByDeletedAtIsNullAndStudent(student);
    }

    @Override
    public List<ExamsDto> studentExamGrades(Student student, Integer year) {
        List<Object[]> gradePostingList = gradePostingRepository.getStudentGrades(student,year);
        List<ExamsDto> examsDto = new ArrayList<>();
        for (Object[] obj : gradePostingList){
            examsDto.add(new ExamsDto((String)obj[0],((Double)obj[1]).floatValue()));

        }

        return examsDto;
    }


}
