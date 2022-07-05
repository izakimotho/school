package lunna.school.service;

import lunna.school.dto.ExamTypeDto;
import lunna.school.model.ExamType;
import lunna.school.model.Organization;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 4:57 PM
 **/

public interface ExamTypeService extends IService<ExamType> {
    List<ExamType> fetchAll(Organization organization);
    List<ExamTypeDto> listAllTypes();
}
