package lunna.school.service;

import lunna.school.model.Fee;
import lunna.school.model.Terms;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/13/22 11:35 AM
 * school
 * FeeService
 * IntelliJ IDEA
 **/
public interface FeeService extends IService<Fee>{
    List<Fee> ListPerClass(UUID class_id);
    List<Fee> ListPerTerm(Terms term);
    List<Fee> ListPerSchool(UUID school_id);

    List<Fee> ListPerTermPerSchool(Terms term, UUID school_id);

    Fee getFeeByClassByTerm(UUID class_id, Terms terms);
}
