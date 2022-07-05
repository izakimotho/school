package lunna.school.service.impl;

import lunna.school.dto.ExamTypeDto;
import lunna.school.dto.GradePostingDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ExamType;
import lunna.school.model.Organization;
import lunna.school.repository.ExamTypeRepository;
import lunna.school.service.ExamTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 4:37 PM
 **/
@Service
public class ExamTypeServiceImpl implements ExamTypeService {

    final ExamTypeRepository examTypeRepository;

    @Autowired
    public ExamTypeServiceImpl(ExamTypeRepository examTypeRepository) {
        this.examTypeRepository = examTypeRepository;
    }

    @Override
    public List<ExamTypeDto> listAllTypes() {
        List<ExamType> examTypes = examTypeRepository.findAllByDeletedAtIsNull();
        ModelMapper modelMapper = new ModelMapper();
        List<ExamTypeDto> examTypeDtos = examTypes.stream().map(
                g -> modelMapper.map(g, ExamTypeDto.class)
        ).collect(Collectors.toList());
        return examTypeDtos;
    }


    @Override
    public List<ExamType> listAll() {
        return null;
    }

    @Override
    public ExamType getById(UUID id) {
        return examTypeRepository.findById(id).orElseThrow(()
                -> new RecordNotFoundException("Exam Type not found: " + id));
    }


    @Override
    public ExamType saveOrUpdate(ExamType examType) {
        return examTypeRepository.saveAndFlush(examType);
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
    public void delete(ExamType examType) {
        try {
            examTypeRepository.delete(examType);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

    }


    @Override
    public List<ExamType> fetchAll(Organization organization) {
        return examTypeRepository.findAllByOrganizationAndDeletedAtIsNull(organization);
    }
}
