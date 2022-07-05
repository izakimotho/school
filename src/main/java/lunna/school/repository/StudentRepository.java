package lunna.school.repository;

import lunna.school.model.GenderEnum;
import lunna.school.model.Hostel;
import lunna.school.model.SchoolStream;
import lunna.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StudentRepository
 *
 * @author Collins K. Sang
 * 2021/06/29 10:11
 **/

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT st FROM Student st WHERE st.gender=?1")
    List<Student> findAllByGender(GenderEnum gender);

    @Query("SELECT st FROM Student st WHERE st.school_stream.organization.org_id =?1")
    List<Student> findAllBySchoolId(UUID school_id);

    @Query("SELECT st FROM Student st " +
            "WHERE st.school_stream.organization.org_id=?1 " +
            "AND  st.school_stream.class_model.class_id =?2")
    List<Student> findAllByClass(UUID school_id, UUID class_id);

    @Query("SELECT count(s.student_id) as hostel_capacity FROM Student s WHERE s.hostel=?1 AND s.deletedAt IS NULL")
    int countStudentsInHostel(Hostel hostel);

    @Query("SELECT count(s.student_id) as class_capacity FROM Student s WHERE s.school_stream=?1 AND s.deletedAt IS NULL")
    int countClassCapacity(SchoolStream schoolStream);

    @Query("SELECT sdt.kin_phone FROM Student sdt WHERE sdt.school_stream.organization.org_id=?1")
    List<String> smsRecipient(UUID school_id);

    @Query(value = "SELECT count(s) FROM Student s WHERE s.organization.org_id =?1")
    Long count(UUID id);

}
