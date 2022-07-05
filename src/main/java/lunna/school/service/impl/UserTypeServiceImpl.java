package lunna.school.service.impl;

import lunna.school.model.UserType;
import lunna.school.repository.UserTypeRepository;
import lunna.school.service.UserTypeService;
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
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeRepository userTypeRepository;
    @Override
    public List<UserType> listAll() {
        return userTypeRepository.findAll();
    }

    @Override
    public UserType getById(UUID id) {
        return null;
    }

    @Override
    public UserType saveOrUpdate(UserType userType) {
        return userTypeRepository.saveAndFlush(userType);
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
    public void delete(UserType userType) {

    }

    @Override
    public UserType getById(Long id) {
        return userTypeRepository.getById(id);
    }
}
