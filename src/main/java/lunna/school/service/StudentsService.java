package lunna.school.service;

import lunna.school.model.GenderEnum;
import lunna.school.model.Hostel;
import lunna.school.model.SchoolStream;
import lunna.school.model.Student;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StudentsService
 *
 * @author Collins K. Sang
 * 2021/06/29 09:38
 **/
public interface StudentsService {
    List<Student> getAllStudents();

    List<Student> getStudentsPerGender(GenderEnum gender);

    List<Student> getStudentsPerSchool(UUID school_id);

    List<Student> getStudentsPerSchoolStream(UUID school_id, UUID class_id);

    Student save(Student student);

    Student getStudentById(UUID id);

    int countHostelCapacity(Hostel hostel);

    int countClassCapacity(SchoolStream class_level);

    void delete(Student student);

    Long count();

    Long count(UUID orgId);

    List<Student> getStudentsPerClass(UUID class_id);
}
