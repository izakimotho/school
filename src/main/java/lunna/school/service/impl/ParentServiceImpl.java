package lunna.school.service.impl;

import lunna.school.model.Organization;
import lunna.school.model.Parent;
import lunna.school.model.Student;
import lunna.school.service.ParentService;
import lunna.school.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 4/26/22, Tuesday
 **/
@Service
public class ParentServiceImpl implements ParentService {
    @Autowired
    public StudentsService studentsService;

    @Override
    public List<Parent> parents() {

        return null;
    }

    @Override
    public List<Parent> schoolParents(UUID school_id) {
        List<Student> students = studentsService.getStudentsPerSchool(school_id);
        List<Parent> parents = students.stream().map(
                student -> new Parent(student.getStudent_id(),student.getGuardians_name(), student.getGuardian_phone())
        ).collect(Collectors.toList());

        return parents;
    }

    @Override
    public Parent studentParent(UUID student_id) {
        Student student = studentsService.getStudentById(student_id);
        Parent parent = null;
        if(student != null){
            parent = new Parent(
                    student.getStudent_id(),
                    student.getGuardians_name(),
                    student.getGuardian_phone());
        }

        return parent;
    }

    @Override
    public List<Parent> classParents(UUID school_id, UUID class_id) {
        List<Student> students = studentsService.getStudentsPerSchoolStream(school_id,class_id);
        List<Parent> parents = students.stream().map(
                s -> new Parent(s.getStudent_id(),s.getGuardians_name(),s.getGuardian_phone())
        ).collect(Collectors.toList());

        return parents;
    }
}
