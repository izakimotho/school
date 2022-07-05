package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Permission;
import lunna.school.repository.PermissionRepository;
import lunna.school.service.PermissionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 19. Jul 2021 11:06 AM
 **/
@Service
public class PermissionServiceImpl implements PermissionService {
    final
    PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> listAll() {
        return permissionRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Permission getById(UUID id) {
        return null;
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.getById(id);
    }

    @Override
    public List<Permission> getByUserLevel(Long level) {
        return permissionRepository.findAllByUserLevel(level);
    }

    @Override
    public Permission saveOrUpdate(Permission permission) {
        return permissionRepository.saveAndFlush(permission);
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
    public void delete(Permission permission) {
        try {
            permissionRepository.delete(permission);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public void softDelete(Permission permission) {
        permission.setDeletedAt(new Date());
        permissionRepository.saveAndFlush(permission);

    }


}
