package lunna.school.service;

import lunna.school.model.TermDetails;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 2:16 PM
 * school
 * TermDetailsService
 * IntelliJ IDEA
 **/
public interface TermDetailsService extends IService<TermDetails>{

    List<TermDetails> findBySchoolId(UUID org_id);
}
