package lunna.school.service;

import lunna.school.model.ClassModel;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 08. Jul 2021 11:19 AM
 **/

public interface ClassService extends IService<ClassModel> {
    List<ClassModel> filterClasses(ClassModel classModel);
    ClassModel getByName(String class_name);
    List<ClassModel> getByEducationAndSchoolLevel(UUID education_system, UUID school_level);

}
