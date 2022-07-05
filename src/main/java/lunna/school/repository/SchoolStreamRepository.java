package lunna.school.repository;

import lunna.school.model.SchoolStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 05. Jul 2021 9:00 AM
 **/

public interface SchoolStreamRepository extends JpaRepository<SchoolStream, UUID> {

    List<SchoolStream> findAllByDeletedAtIsNull();

    @Query("SELECT sn FROM SchoolStream sn WHERE sn.stream_name = ?1")
    SchoolStream getByName(String stream_name);

    @Query(value = "SELECT sch FROM SchoolStream sch WHERE sch.organization.org_id = ?1 AND sch.deletedAt IS NULL ")
    List<SchoolStream> findSchoolStreamBySchoolId(UUID school_id);

    @Modifying
    @Query(value = "UPDATE SchoolStream c SET c.deletedAt = CURRENT_DATE WHERE c.school_stream_id =?1")
    void deleteSchoolStream(UUID school_stream_id);

    @Query(value = "SELECT count(sc) FROM SchoolStream sc WHERE sc.organization.org_id =?1")
    Long count(UUID orgId);


}
