package lunna.school.repository;

import lunna.school.dto.ExamsDto;
import lunna.school.model.ExamSchedule;
import lunna.school.model.GradePosting;
import lunna.school.model.Organization;
import lunna.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 10:55 AM
 **/

public interface GradePostingRepository extends JpaRepository<GradePosting, UUID> {
    List<GradePosting> findAllByDeletedAtIsNull();
    List<GradePosting> findAllByDeletedAtIsNullAndOrganization(Organization organization);
    @Query(value = "select g from GradePosting g where g.exam_schedule =?1 and g.deletedAt is null")
    List<GradePosting> findAllByDeletedAtIsNullAndExamSchedule(ExamSchedule examSchedule);
    List<GradePosting> findAllByDeletedAtIsNullAndStudent(Student student);

    @Query(value = "select g.exam_schedule.exam_schedule_name AS exam,sum (g.grade)/count (g.grade_posting_id) as grade " +
            "from GradePosting  g where g.student =?1 and FUNCTION('year', g.exam_schedule.exam_date) =?2 " +
            "and g.deletedAt is null group by g.exam_schedule.exam_schedule_name,g.exam_schedule,g.exam_schedule.terms, g.exam_schedule.exam_type")
    List<Object[]> getStudentGrades(Student student, Integer exam_year);
}
