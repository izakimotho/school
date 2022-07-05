package lunna.school.repository;

import lunna.school.model.Hostel;
import lunna.school.model.Organization;
import lunna.school.model.Staff;
import lunna.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * HostelRepository
 *
 * @author Collins K. Sang
 * 2021/07/02 11:24
 **/
public interface HostelRepository extends JpaRepository<Hostel, UUID> {
    @Query("SELECT h FROM Hostel h WHERE h.org_id=?1")
    List<Hostel> findAllByOrgId(Organization organization);

    @Query("SELECT h FROM Hostel h WHERE h.org_id.org_id=?1")
    List<Hostel> findAllByOrgId(UUID organization);

    @Query("SELECT h FROM Hostel h WHERE h.hostel_name=?1")
    Hostel findByName(String hostelName);

    @Query("SELECT h FROM Hostel h WHERE h.hostel_captain=?1 AND h.org_id=?2 AND h.hostel_name=?3")
    Hostel ifExists(Student hostel_captain, Organization organization, String hostel_name);

    @Query("SELECT h FROM Hostel h WHERE h.hostel_master=?1 AND h.org_id=?2 AND h.hostel_name=?3")
    Hostel ifExistsMaster(Staff hostel_master, Organization organization, String hostel_name);

    @Query("UPDATE Hostel h SET h.deletedAt=CURRENT_DATE WHERE h.org_id=?1")
    void deleteHostel(Hostel hostel_id);
}
