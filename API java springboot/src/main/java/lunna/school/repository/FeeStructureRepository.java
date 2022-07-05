package lunna.school.repository;

import lunna.school.dto.FeeStructureDetailsDto;
import lunna.school.dto.FeeStructureDtos;
import lunna.school.model.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/19/22 9:54 AM
 * school
 * FeeStructureRepository
 * IntelliJ IDEA
 **/
@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, UUID> {

    @Query("SELECT fs FROM FeeStructure fs " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            "AND fs.academic_year.organization.org_id=?2 " +
            "ORDER BY fs.class_model.class_name ASC ")
    List<FeeStructure> findBySchoolId(UUID academic_year, UUID org_id);

    @Query("SELECT fs " +
            "FROM FeeStructure fs " +
            " LEFT JOIN VoteHeadFee vf ON vf.feeStructure = fs.fee_structure_id " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            " AND vf.termDetails.terms.term_id= ?2 " +
            "AND fs.academic_year.organization.org_id=?3 " +
            "ORDER BY fs.class_model.class_name ASC")
    List<FeeStructure> findBySchoolIdAndTerm(UUID academic_year, UUID term, UUID org_id);

    @Query("SELECT fs " +
            "FROM FeeStructure fs " +
            " LEFT JOIN VoteHeadFee vf ON vf.feeStructure = fs.fee_structure_id " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            " AND vf.termDetails.terms.term_id= ?2 " +
            "AND fs.academic_year.organization.org_id=?3 " +
            "AND fs.class_model.class_id=?4")
    List<FeeStructure> findBySchoolIdAndClassAndTerm(UUID academic_year, UUID term, UUID org_id, UUID class_id);

    @Query("SELECT fs " +
            "FROM FeeStructure fs " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            "AND fs.academic_year.organization.org_id=?2 " +
            "AND fs.class_model.class_id=?3")
    List<FeeStructure> findBySchoolIdAndClassAndYear(UUID academic_year, UUID org_id, UUID class_id);
    @Query("SELECT fs " +
            "FROM FeeStructure fs " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            "AND   fs.academic_year.organization.org_id=?2" +
            " GROUP BY fs.class_model" +
            " ORDER BY fs.class_model.class_name ASC ")
    List<FeeStructure> findBySchoolIdAndYear(UUID academic_year, UUID org_id);

    @Query("DELETE " +
            " FROM FeeStructure fs" +
            " WHERE fs.academic_year.academic_year_id=?1 " +
            " AND fs.academic_year.organization.org_id = ?2")
    void deletePerYear(UUID academic_year, UUID org_id);

    @Query("DELETE " +
            " FROM FeeStructure fs" +
            " WHERE fs.class_model.class_id=?1 " +
            " AND fs.academic_year.organization.org_id = ?2")
    void deletePerClass(UUID class_id, UUID org_id);


    @Query("SELECT new lunna.school.dto.FeeStructureDetailsDto(sum(vf.amount), fs.class_model, fs.academic_year, fs.fee_structure_id) " +
            " FROM FeeStructure fs" +
            " LEFT JOIN VoteHeadFee vf ON vf.feeStructure.fee_structure_id = fs.fee_structure_id " +
            " WHERE fs.academic_year.academic_year_id =?1" +
            " AND fs.academic_year.organization.org_id =?2 " +
            " GROUP BY fs.class_model, fs.academic_year, fs.fee_structure_id")
    List<FeeStructureDetailsDto> findSumFeePerSchool(UUID academic_year_id, UUID org_id);

//    @Query("SELECT new lunna.school.dto.FeeStructureDtos(fs.fee_structure_id, sum(fs.amount), fs.class_model, fs.academic_year)" +
//            " FROM FeeStructure fs " +
//            " WHERE fs.fee_vote.organization.org_id = ?1 " +
//            "AND fs.class_model.class_id = ?2 " +
//            "GROUP BY fs.fee_structure_id")
@Query(" SELECT fs " +
        " FROM FeeStructure fs " +
        " LEFT JOIN VoteHeadFee vf ON vf.feeStructure = fs.fee_structure_id " +
        " WHERE fs.academic_year.organization.org_id = ?1 " +
        " AND fs.class_model.class_id = ?2 " +
        " ORDER BY vf.termDetails DESC")
    List<FeeStructure> findByClass(UUID org_id, UUID class_id);


    @Query(" SELECT fs " +
            " FROM FeeStructure fs " +
            " WHERE fs.fee_structure_id = ?1 " +
            " ORDER BY fs.fee_structure_id DESC")
    FeeStructure findByFeeStructureId(UUID feeStructureId);

    @Query("SELECT fs FROM FeeStructure fs " +
            "WHERE fs.academic_year.academic_year_id = ?1 " +
            "AND fs.class_model.class_id=?2 " +
            "ORDER BY fs.class_model.class_name   ")
    FeeStructure findByAcademicYearAndClassModel(UUID academic_year, UUID class_id);
}
