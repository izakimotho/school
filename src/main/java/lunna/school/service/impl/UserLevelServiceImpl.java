package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.UserLevel;
import lunna.school.repository.UserLevelRepository;
import lunna.school.service.UserLevelService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 12:11 PM
 **/
@Service
public class UserLevelServiceImpl implements UserLevelService {
    final
    UserLevelRepository userLevelRepository;

    public UserLevelServiceImpl(UserLevelRepository userLevelRepository) {
        this.userLevelRepository = userLevelRepository;
    }

    @Override
    public List<UserLevel> listAll() {
        return userLevelRepository.findAll();
    }

    @Override
    public UserLevel getById(UUID id) {
        return null;
    }

    @Override
    public UserLevel getById(Integer id) {
        return userLevelRepository.getById(id);
    }


    @Override
    public UserLevel saveOrUpdate(UserLevel userLevel) {
        return userLevelRepository.saveAndFlush(userLevel);
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
    public void delete(UserLevel userLevel) {
        try {
            userLevelRepository.delete(userLevel);
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

    }

    @Override
    public UserLevel getByName(String category_name) {
        return userLevelRepository.getByName(category_name);
    }
}
