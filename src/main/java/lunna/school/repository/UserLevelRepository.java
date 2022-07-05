package lunna.school.repository;

import lunna.school.model.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 9:08 AM
 **/

public interface UserLevelRepository extends JpaRepository<UserLevel, Integer> {
    @Query("SELECT uc FROM UserLevel uc WHERE uc.category_name=?1")
    UserLevel getByName(String category_name);
}
