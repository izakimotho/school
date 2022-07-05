package lunna.school.repository;

import lunna.school.dto.*;
import lunna.school.model.StudentFee;
import lunna.school.model.Terms;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/16/22 9:55 AM
 * school
 * StudentFeeRepository
 * IntelliJ IDEA
 **/
@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, UUID> {
    @Query("SELECT new lunna.school.dto.FeeRecordDto(sf.student_id, SUM(sf.fee_amount) as paid_amount, sf.terms, sf.class_id, sf.school_stream_id, sf.academic_year )" +
            " FROM StudentFee sf" +
            " WHERE sf.org_id.org_id = ?1" +
            " GROUP BY sf.student_id , sf.terms, sf.class_id, sf.school_stream_id,sf.academic_year, sf.class_id.class_name" +
            " ORDER BY sf.class_id.class_name ASC ")
    List<FeeRecordDto> findByOrg_id(UUID org_id);

    @Query("SELECT sf" +
            " FROM StudentFee sf" +
            " WHERE sf.student_id.student_id = ?1")
    List<StudentFee> getByStudent_id(UUID student_id);

    //    @Query("SELECT sf FROM StudentFee sf WHERE sf.class_id.class_id =?1")
    @Query("SELECT new lunna.school.dto.FeeRecordDto(sf.student_id, SUM(sf.fee_amount) as paid_amount, sf.terms, sf.class_id, sf.school_stream_id, sf.academic_year )" +
            " FROM StudentFee sf" +
            " WHERE sf.class_id.class_id =?1" +
            " GROUP BY sf.student_id , sf.terms, sf.class_id, sf.school_stream_id,sf.academic_year, sf.class_id.class_name")
    List<FeeRecordDto> getFeeByClass(UUID class_id);

//    @Query("SELECT sf FROM StudentFee sf WHERE sf.terms =?1 AND sf.org_id.org_id=?2")

    @Query("SELECT new lunna.school.dto.FeeRecordDto(sf.student_id, SUM(sf.fee_amount) as paid_amount, sf.terms, sf.class_id, sf.school_stream_id, sf.academic_year )" +
            " FROM StudentFee sf" +
            " WHERE sf.terms.term_details_id =?1" +
            " AND sf.org_id.org_id=?2" +
            " GROUP BY sf.student_id , sf.terms, sf.class_id, sf.school_stream_id,sf.academic_year, sf.class_id.class_name")
    List<FeeRecordDto> getFeeByTermBySchool(UUID terms, UUID school_id);

    //    @Query("SELECT sf FROM StudentFee sf WHERE sf.terms =?1 AND sf.class_id.class_id=?2")
    @Query("SELECT new lunna.school.dto.FeeRecordDto(sf.student_id, SUM(sf.fee_amount) as paid_amount, sf.terms, sf.class_id, sf.school_stream_id, sf.academic_year )" +
            " FROM StudentFee sf" +
            " WHERE sf.terms.term_details_id =?1" +
            " AND sf.class_id.class_id=?2" +
            " GROUP BY sf.student_id , sf.terms, sf.class_id, sf.school_stream_id,sf.academic_year, sf.class_id.class_name")
    List<FeeRecordDto> getFeeByTermByClass(UUID terms, UUID class_id);

    @Query("SELECT sf" +
            " FROM StudentFee sf" +
            " WHERE sf.terms.term_details_id=?1" +
            " AND sf.student_id.student_id=?2")
    List<StudentFee> getFeeByTermByStudent(UUID terms, UUID student_id);

    @Query("SELECT SUM(sf.fee_amount) AS fee_amount" +
            " FROM StudentFee sf" +
            " WHERE sf.terms.term_details_id=?1" +
            " AND sf.student_id.student_id=?2")
    Float getFeeSumByTermByStudent(UUID terms, UUID student_id);

    @Query("DELETE " +
            " FROM StudentFee sf" +
            " WHERE sf.class_id.class_id=?1")
    void deleteByClass(UUID class_id);

    @Query("DELETE " +
            " FROM StudentFee sf" +
            " WHERE sf.org_id.org_id=?1")
    void deleteBySchool(UUID school_id);
}
