package lunna.school.repository;

import lunna.school.model.EducationSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 5:43 PM
 **/

public interface EducationSystemRepository extends JpaRepository<EducationSystem, UUID> {
}
