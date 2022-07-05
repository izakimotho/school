package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.Hostel;
import lunna.school.model.Organization;
import lunna.school.model.Staff;
import lunna.school.model.Student;
import lunna.school.repository.HostelRepository;
import lunna.school.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * HostelServiceImpl
 *
 * @author Collins K. Sang
 * 2021/07/02 11:21
 **/
@Service
public class HostelServiceImpl implements HostelService {

    final HostelRepository hostelRepository;

    @Autowired
    public HostelServiceImpl(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    @Override
    public List<Hostel> getAll() {
        return hostelRepository.findAll();
    }

    @Override
    public List<Hostel> getListPerSchool(Organization organization) {
        return hostelRepository.findAllByOrgId(organization);
    }

    @Override
    public List<Hostel> getListPerSchool(UUID id) {
        return hostelRepository.findAllByOrgId(id);
    }

    @Override
    public Hostel getPerName(String hostelName) {
        return hostelRepository.findByName(hostelName);
    }

    @Override
    public Hostel save(Hostel hostel) {
        try {
            return hostelRepository.save(hostel);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

    }

    @Override
    public Hostel update(Hostel hostel) {
        return hostelRepository.save(hostel);
    }

    @Override
    public Hostel getById(String hostel_id) {
        return hostelRepository.getById(UUID.fromString(hostel_id));
    }

    @Override
    public Hostel delete(Hostel hostel_id) {
        try {
            hostelRepository.delete(hostel_id);
            return hostel_id;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Hostel getIfExist(Student hostel_captain, Organization organization, String hostel_name) {
        return hostelRepository.ifExists(hostel_captain, organization, hostel_name);
    }

    @Override
    public Hostel getIfExistHostelMaster(Staff hostel_master, Organization organization, String hostel_name) {
        return hostelRepository.ifExistsMaster(hostel_master, organization, hostel_name);
    }
}
