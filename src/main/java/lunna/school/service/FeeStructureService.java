package lunna.school.service;

import lunna.school.dto.*;
import lunna.school.model.FeeStructure;
import lunna.school.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/19/22 9:54 AM
 * school
 * FeeStructureService
 * IntelliJ IDEA
 **/
public interface FeeStructureService extends IService<FeeStructure> {
    List<FeeStructure> findBySchoolId(UUID academic_year, UUID org_id);

    List<FeeStructure> findBySchoolIdAndTerm(UUID academic_year, UUID term, UUID org_id);

    List<FeeStructure> findBySchoolIdAndClassAndTerm(UUID academic_year, UUID term, UUID org_id, UUID class_id);

    List<FeeStructure> findBySchoolIdAndClassAndYear(UUID academic_year, UUID org_id, UUID class_id);

    List<FeeStructure> findBySchoolIdAndYear(UUID academic_year, UUID org_id);

    void deletePerYear(UUID academic_year, UUID org_id);

    void deletePerClass(UUID class_id, UUID org_id);

    List<FeeStructureDetailsDto> findSumFeePerSchool(UUID academic_year_id, UUID org_id);

    List<FeeStructureDtos> findBySchoolIdAndClass(UUID org_id, UUID fromString);

    FeeStructureDTO getBySchoolAndClass(UUID org_id, UUID class_id);

    FeeStructureDTO getByFeeStructureId(UUID feeStructureId);

    FeeStructureDto saveFeeStructure(FeeStructureReq feeStructureReq, User user);
}
