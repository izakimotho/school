package lunna.school.service;

import lunna.school.model.AcademicYear;

import java.util.List;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
public interface AcademicYearService extends IService<AcademicYear> {
    List<AcademicYear> getAcademicYearBySchool(UUID school_id);
    AcademicYear getActiveAcademicYearBySchool(UUID school_id);
}
