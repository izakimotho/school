package lunna.school.service.impl;

import lunna.school.dto.ProfileDto;
import lunna.school.dto.UserDto;
import lunna.school.dto.UserDtoExt;
import lunna.school.exception.BadRequestException;
import lunna.school.model.*;
import lunna.school.repository.RoleRepository;
import lunna.school.repository.UserRepository;
import lunna.school.service.RoleService;
import lunna.school.service.StaffService;
import lunna.school.service.UserService;
import lunna.school.service.UserTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 8:07 AM
 **/
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserTypeService userTypeService;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDtoExt> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u ->
                        modelMapper.map(u, UserDtoExt.class))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Boolean userExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<UserType> getUserTypes() {
        return userTypeService.listAll();
    }

    @Override
    public User profile(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return userRepository.countByOrg(id);
    }
}
