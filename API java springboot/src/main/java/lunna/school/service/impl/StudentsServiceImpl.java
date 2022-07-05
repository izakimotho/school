package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.GenderEnum;
import lunna.school.model.Hostel;
import lunna.school.model.SchoolStream;
import lunna.school.model.Student;
import lunna.school.repository.StudentRepository;
import lunna.school.service.StudentsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StudentsServiceImpl
 *
 * @author Collins K. Sang
 * 2021/06/29 09:38
 **/
@Service
public class StudentsServiceImpl implements StudentsService {

    private final StudentRepository studentRepository;

    public StudentsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentsPerGender(GenderEnum gender) {
        return studentRepository.findAllByGender(gender);
    }

    @Override
    public List<Student> getStudentsPerSchool(UUID school_id) {
        return studentRepository.findAllBySchoolId(school_id);
    }

    @Override
    public List<Student> getStudentsPerSchoolStream(UUID school_id, UUID class_id) {
        return studentRepository.findAllByClass(school_id, class_id);
    }

    @Override
    public Student save(Student student) {
        Student student1;
        try {
            student1 = studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Error: " + e.getMostSpecificCause().getMessage());
        }
        return student1;
    }

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).orElseThrow(()->
                new RecordNotFoundException("Student not Found:"+id));
    }

    @Override
    public int countHostelCapacity(Hostel hostel) {
        return studentRepository.countStudentsInHostel(hostel);
    }

    @Override
    public int countClassCapacity(SchoolStream class_level) {
        return studentRepository.countClassCapacity(class_level);
    }

    @Override
    public void delete(Student student) {
        try {
            studentRepository.delete(student);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Long count() {
        return studentRepository.count();
    }

    @Override
    public Long count(UUID orgId) {
        return studentRepository.count(orgId);
    }

    @Override
    public List<Student> getStudentsPerClass(UUID class_id) {
        return null;
    }
}
