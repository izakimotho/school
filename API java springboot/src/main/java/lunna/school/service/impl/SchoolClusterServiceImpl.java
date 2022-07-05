package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.SchoolCluster;
import lunna.school.repository.SchoolClusterRepository;
import lunna.school.service.SchoolClusterService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:34 PM
 **/
@Service
public class SchoolClusterServiceImpl implements SchoolClusterService {
    final
    SchoolClusterRepository schoolClusterRepository;

    public SchoolClusterServiceImpl(SchoolClusterRepository schoolClusterRepository) {
        this.schoolClusterRepository = schoolClusterRepository;
    }

    @Override
    public List<SchoolCluster> listAll() {
        return schoolClusterRepository.findAll();
    }

    @Override
    public SchoolCluster getById(UUID id) {
        return schoolClusterRepository.getById(id);
    }


    @Override
    public SchoolCluster saveOrUpdate(SchoolCluster schoolCluster) {
        return schoolClusterRepository.saveAndFlush(schoolCluster);
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
    public void delete(SchoolCluster schoolCluster) {
        try {
            schoolClusterRepository.delete(schoolCluster);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public SchoolCluster getByName(String cluster_name) {
        return schoolClusterRepository.getByName(cluster_name);
    }
}
