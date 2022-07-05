package lunna.school.service;

import lunna.school.dto.SubjectDetailsDto;
import lunna.school.model.Subject;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 12:09 PM
 **/

public interface SubjectService extends IService<Subject>{
    List<Subject> filterSubjects(Subject subject);
    SubjectDetailsDto getSubjectDetails(String subject_id);
    List<Subject> getSubjectsBySchoolLevel(UUID school_level_id);
    List<Subject> getSubjectsByEducationSystem(UUID education_system_id);
    void softDelete(Subject subject);
    Subject getByName(String subject_name);
}
