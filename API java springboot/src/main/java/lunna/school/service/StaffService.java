package lunna.school.service;

import lunna.school.model.GenderEnum;
import lunna.school.model.Staff;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StaffService
 *
 * @author Collins K. Sang
 * 2021/06/30 10:10
 **/
public interface StaffService {
    List<Staff> getAllStaff();
    List<Staff> getStaffPerGender(GenderEnum gender);
    List<Staff> getStaffPerSchool(UUID school_id);
    Staff save(Staff staff);
    Staff getStaffById(UUID id);
    Staff getStaffByEmail(String email);

    Staff getStaffByPositionAndSchool(UUID position_id, UUID org_id);
    Staff getStaffByUserId(UUID user_id);
    Long count();
    Long count(UUID id);
    void delete(Staff staff);
}
