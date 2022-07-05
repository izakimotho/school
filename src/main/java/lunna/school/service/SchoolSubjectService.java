package lunna.school.service;

import lunna.school.model.SchoolSubject;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/16/22, Monday
 **/
public interface SchoolSubjectService extends IService<SchoolSubject> {
    List<SchoolSubject> getSubjectBYSchool(UUID orgId);
}
