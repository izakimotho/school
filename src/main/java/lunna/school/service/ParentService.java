package lunna.school.service;

import lunna.school.model.Organization;
import lunna.school.model.Parent;
import lunna.school.model.Student;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 4/26/22, Tuesday
 **/
public interface ParentService {
    List<Parent> parents();
    List<Parent> schoolParents(UUID school_id);
    Parent studentParent(UUID student_id);

    List<Parent> classParents(UUID school_id, UUID class_id);
}
