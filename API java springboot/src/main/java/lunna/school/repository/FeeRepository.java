package lunna.school.repository;

import lunna.school.model.Fee;
import lunna.school.model.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/13/22 4:16 PM
 * school
 * FeeRepository
 * IntelliJ IDEA
 **/
@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {

    @Query("SELECT f FROM Fee f WHERE f.class_model.class_id=?1")
    List<Fee> findByClass_id(UUID class_id);

    List<Fee> findByTerms(Terms term);

    @Query("SELECT f FROM Fee f WHERE f.organization.org_id=?1")

    List<Fee> findBySchool(UUID school_id);
    @Query("SELECT f FROM Fee f WHERE f.terms = ?1 AND f.organization.org_id=?2")
    List<Fee> findByTermsBySchool(Terms terms, UUID school_id);
    @Query("SELECT f FROM Fee f WHERE f.class_model.class_id = ?1 AND f.terms = ?2")
    Fee getFeeByClassByTerm(UUID class_id, Terms terms);
}
