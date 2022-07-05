package lunna.school.service;

import lunna.school.dto.FeeRecordDto;
import lunna.school.model.StudentFee;
import lunna.school.model.Terms;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/16/22 9:56 AM
 * school
 * StudentFeeService
 * IntelliJ IDEA
 **/
public interface StudentFeeService extends IService<StudentFee> {
    List<FeeRecordDto> ListPerSchool(UUID org_id);
    List<StudentFee> getFeeByStudent(UUID student_id);

    List<FeeRecordDto> getFeeByClass(UUID class_id);

    List<FeeRecordDto> getFeeByTermBySchool(UUID terms, UUID school_id);

    List<FeeRecordDto> getFeeByTermByClass(UUID terms, UUID class_id);

    List<StudentFee> getFeeByTermByStudent(UUID terms, UUID student_id);

    Float getFeeSumByTermByStudent(UUID terms, UUID student_id);

    void deleteByStudent(List<StudentFee> studentFee);

    void deleteByClass(UUID class_id);

    void deleteBySchool(UUID school_id);
}
