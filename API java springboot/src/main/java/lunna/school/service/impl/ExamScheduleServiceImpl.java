package lunna.school.service.impl;

import lunna.school.dto.ExamScheduleDto;
import lunna.school.dto.ExamTypeDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ExamSchedule;
import lunna.school.model.Organization;
import lunna.school.repository.ExamScheduleRepository;
import lunna.school.service.ExamScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 7:49 AM
 **/
@Service
public class ExamScheduleServiceImpl implements ExamScheduleService {

    final ExamScheduleRepository examScheduleRepository;

    @Autowired
    public ExamScheduleServiceImpl(ExamScheduleRepository examScheduleRepository) {
        this.examScheduleRepository = examScheduleRepository;
    }

    @Override
    public List<ExamSchedule> listAll() {
        return examScheduleRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public ExamSchedule getById(UUID id) {
        return examScheduleRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Example Schedule not found" + id) );
    }

    @Override
    public List<ExamScheduleDto> listAllSchedule() {
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAllByDeletedAtIsNull();
        ModelMapper modelMapper = new ModelMapper();
        List<ExamScheduleDto> examScheduleDtos = examScheduleList.stream().map(
                g -> modelMapper.map(g, ExamScheduleDto.class)
        ).collect(Collectors.toList());
        return examScheduleDtos;
    }

    @Override
    public List<ExamScheduleDto> fetchAll(Organization organization) {
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAllByOrganizationAndDeletedAtIsNull(organization);
        ModelMapper modelMapper = new ModelMapper();
        List<ExamScheduleDto> examScheduleDtos = examScheduleList.stream().map(
                g -> modelMapper.map(g, ExamScheduleDto.class)
        ).collect(Collectors.toList());
        return examScheduleDtos;
    }

    @Override
    public List<ExamScheduleDto> fetchByClassOrg(UUID school_stream_id, UUID org_id) {
        List<ExamSchedule> examScheduleList = examScheduleRepository.fetchByClassOrg(school_stream_id, org_id);
        ModelMapper modelMapper = new ModelMapper();
        List<ExamScheduleDto> examScheduleDtos = examScheduleList.stream().map(
                g -> modelMapper.map(g, ExamScheduleDto.class)
        ).collect(Collectors.toList());
        return examScheduleDtos;

    }


    @Override
    public ExamScheduleDto getByIdExams(UUID id) {
        ExamSchedule examSchedule = examScheduleRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("ExamSchedule not found: " + id));
        ModelMapper modelMapper = new ModelMapper();
        ExamScheduleDto examScheduleDto = modelMapper.map(examSchedule, ExamScheduleDto.class);
        return examScheduleDto;
    }


    @Override
    public ExamSchedule saveOrUpdate(ExamSchedule examSchedule) {
        try {
            return examScheduleRepository.saveAndFlush(examSchedule);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

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
    public void delete(ExamSchedule examSchedule) {
        examSchedule.setDeletedAt(new Date());
        try {
            examScheduleRepository.delete(examSchedule);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

    }
}
