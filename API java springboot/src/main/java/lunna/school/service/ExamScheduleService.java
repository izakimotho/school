package lunna.school.service;

import lunna.school.dto.ExamScheduleDto;
import lunna.school.model.ExamSchedule;
import lunna.school.model.Organization;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 7:50 AM
 **/

public interface ExamScheduleService extends IService<ExamSchedule> {
    List<ExamScheduleDto> fetchAll(Organization organization);
    List<ExamScheduleDto> fetchByClassOrg(UUID class_id, UUID org_id);
    List<ExamScheduleDto> listAllSchedule();
    ExamScheduleDto getByIdExams(UUID id);
}
