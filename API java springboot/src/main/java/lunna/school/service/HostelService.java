package lunna.school.service;

import lunna.school.model.Hostel;
import lunna.school.model.Organization;
import lunna.school.model.Staff;
import lunna.school.model.Student;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * HostelService
 *
 * @author Collins K. Sang
 * 2021/07/02 11:22
 **/
public interface HostelService {

    List<Hostel> getAll();

    List<Hostel> getListPerSchool(Organization organization);

    List<Hostel> getListPerSchool(UUID id);

    Hostel getPerName(String hostelName);

    Hostel save(Hostel hostel);

    Hostel update(Hostel hostel);

    Hostel getById(String hostel_id);

    Hostel delete(Hostel hostel_id);

    Hostel getIfExist(Student hostel_captain, Organization organization, String hostel_name);

    Hostel getIfExistHostelMaster(Staff hostel_master, Organization organization, String hostel_name);
}
