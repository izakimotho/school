package lunna.school.service.impl;

import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.GenderEnum;
import lunna.school.model.Staff;
import lunna.school.repository.StaffRepository;
import lunna.school.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StaffServiceImpl
 *
 * @author Collins K. Sang
 * 2021/06/30 10:10
 **/
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public List<Staff> getStaffPerGender(GenderEnum gender) {
        return staffRepository.findAllByGender(gender);
    }

    @Override
    public List<Staff> getStaffPerSchool(UUID school_id) {
        return staffRepository.findAllBySchoolId(school_id);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff getStaffById(UUID id) {
        return staffRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Staff not Found"+ id)

        );
    }

    @Override
    public Staff getStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public Staff getStaffByPositionAndSchool(UUID position_id, UUID org_id) {
        return staffRepository.getStaffByPositionAndSchool(position_id, org_id);
    }

    @Override
    public Staff getStaffByUserId(UUID user_id) {
        return staffRepository.findByUserId(user_id);
    }

    @Override
    public Long count() {
        return staffRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return staffRepository.countByOrg(id);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.delete(staff);

    }
}
