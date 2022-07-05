package lunna.school.service;

import lunna.school.dto.SchoolDtoExt;
import lunna.school.model.Organization;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:46 AM
 **/

public interface SchoolService {
    Organization saveSchool(SchoolDtoExt schoolDto);

    List<Organization> listSchools();
    List<Organization> listSchools(UUID id);

    Organization getSchoolById(UUID id);

    Organization updateSchool(SchoolDtoExt sch);

    void delete(Organization org);

    void softDelete(Organization org);
}
