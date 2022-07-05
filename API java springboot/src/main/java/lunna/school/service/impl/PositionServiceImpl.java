package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.Positions;
import lunna.school.model.UserType;
import lunna.school.repository.PositionsRepository;
import lunna.school.service.PositionService;
import lunna.school.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * PositionService
 *
 * @author Collins K. Sang
 * 2021/07/08 14:36
 **/
@Service
public class PositionServiceImpl implements PositionService {
    final PositionsRepository positionsRepository;
    @Autowired
    UserTypeService userTypeService;

    @Autowired
    public PositionServiceImpl(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    @Override
    public List<Positions> listAll() {
        return positionsRepository.findAll();
    }


    @Override
    public Positions getById(UUID id) {
        return positionsRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Position not found: "+ id));
    }


    @Override
    public Positions saveOrUpdate(Positions positions) {
        return positionsRepository.save(positions);
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
    public void delete(Positions positions) {
        try {
            positionsRepository.delete(positions);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Positions getByName(String position_name) {
        return positionsRepository.getByName(position_name);
    }

    @Override
    public List<Positions> getBySchool(UUID organization) {
        return positionsRepository.getBySchool(organization);
    }

    @Override
    public List<Positions> getPerSchoolAndCategory(UUID organization, UserType category) {
        return positionsRepository.getPerSchoolAndCategory(organization, category);
    }

    @Override
    public List<Positions> getPerCategory(UserType category) {
        return positionsRepository.getPerCategory(category);
    }

    @Override
    public List<UserType> getLeadershipTypes() {

        return userTypeService.listAll();
    }
}
