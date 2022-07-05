package lunna.school.service;

import lunna.school.dto.ExamsDto;
import lunna.school.dto.GradePostingDto;
import lunna.school.model.ExamSchedule;
import lunna.school.model.GradePosting;
import lunna.school.model.Organization;
import lunna.school.model.Student;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 10:56 AM
 **/
public interface GradePostingService extends IService<GradePosting> {
    List<GradePostingDto> listByOrg(Organization organization);
    void softDelete(GradePosting gradePosting);
    List<GradePosting> listByExamSchedule(ExamSchedule examSchedule);
    List<GradePosting> studentExams(Student student);
    List<ExamsDto> studentExamGrades(Student student, Integer year);
}
