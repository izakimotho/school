package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Role;
import lunna.school.repository.RoleRepository;
import lunna.school.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 6:12 PM
 **/
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(UUID id) {
        return roleRepository.findByUUID(id);
    }


    @Override
    public Role saveOrUpdate(Role role) {
        Role roles;
        try {
            roles = roleRepository.saveAndFlush(role);
            return roles;
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
    public void delete(Role role) {
        try {
            roleRepository.delete(role);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Role getByName(String role_name) {
        return roleRepository.findByRole_name(role_name);
    }

    @Override
    public Role getByNameOrgId(String role_name, UUID org_id) {
        return roleRepository.findByRoleNameOrgId(role_name,org_id);
    }

    @Override
    public List<Role> getByOrgId(UUID org_id) {
        return roleRepository.findByOrgId(org_id);
    }
}
