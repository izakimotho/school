package lunna.school.repository;

import lunna.school.model.StreamDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 09. Jul 2021 9:06 AM
 **/
@Transactional
public interface StreamDetailsRepository extends JpaRepository<StreamDetails, UUID> {

    @Query(value = "SELECT s FROM StreamDetails s WHERE s.school_stream.school_stream_id =?1")
    List<StreamDetails> getStreamDetailsBySchool_stream(UUID school_stream_id);

    @Query(value = "SELECT s FROM StreamDetails s WHERE s.organization.org_id =?1")
    List<StreamDetails> getStreamDetailsBySchool(UUID school_id);
}
