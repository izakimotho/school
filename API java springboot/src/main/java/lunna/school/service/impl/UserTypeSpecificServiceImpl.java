package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Organization;
import lunna.school.model.UserTypeSpecific;
import lunna.school.repository.UserTypeSpecificRepository;
import lunna.school.service.UserTypeSpecificService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * UserTypeSpecificService
 *
 * @author Collins K. Sang
 * 2021/07/13 15:26
 **/
@Service
public class UserTypeSpecificServiceImpl implements UserTypeSpecificService {

    final UserTypeSpecificRepository userTypeSpecificRepository;

    @Autowired
    public UserTypeSpecificServiceImpl(UserTypeSpecificRepository userTypeSpecificRepository) {
        this.userTypeSpecificRepository = userTypeSpecificRepository;
    }

    @Override
    public List<UserTypeSpecific> listAll() {
        return userTypeSpecificRepository.findAllByDeletedAtIsNull();
    }


    @Override
    public UserTypeSpecific getById(UUID id) {
        return userTypeSpecificRepository.getById(id);
    }


    @Override
    public UserTypeSpecific saveOrUpdate(UserTypeSpecific userTypeSpecific) {
        return userTypeSpecificRepository.save(userTypeSpecific);
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
    public void delete(UserTypeSpecific userTypeSpecific) {
        try {
            userTypeSpecificRepository.delete(userTypeSpecific);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public UserTypeSpecific getByName(String type_name) {
        return userTypeSpecificRepository.findByName(type_name);
    }

    public List<UserTypeSpecific> getPerOrganization(Organization organization) {
        return userTypeSpecificRepository.findByOrganization(organization);
    }
}
