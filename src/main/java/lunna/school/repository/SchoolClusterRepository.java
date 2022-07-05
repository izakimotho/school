package lunna.school.repository;

import lunna.school.model.SchoolCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 12:11 PM
 **/

public interface SchoolClusterRepository extends JpaRepository<SchoolCluster, UUID> {

    @Query("SELECT sc FROM SchoolCluster sc WHERE sc.cluster_name = ?1")
    SchoolCluster getByName(String cluster_name);
}
