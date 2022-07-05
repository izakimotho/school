package lunna.school.service;

import lunna.school.model.Grades;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:14 PM
 **/
public interface GradesService extends IService<Grades> {
    Grades getByName(String grade_name);
    List<Grades> getPerSchool(UUID school_id);
}
