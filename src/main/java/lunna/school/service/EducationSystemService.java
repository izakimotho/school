package lunna.school.service;

import lunna.school.model.EducationSystem;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 5:44 PM
 **/

public interface EducationSystemService {
    EducationSystem create(EducationSystem educationSystem);
    EducationSystem getById(UUID id);
    List<EducationSystem> getList();
    EducationSystem update(EducationSystem educationSystem);
    void delete(EducationSystem educationSystem);
}
