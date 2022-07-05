package lunna.school.service;

import lunna.school.model.Terms;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 10:31 AM
 * school
 * TermsService
 * IntelliJ IDEA
 **/
public interface TermsService extends IService<Terms> {
    List<Terms> findBySchoolId(UUID org_id);
}
