package lunna.school.repository;

import lunna.school.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 18. Aug 2021 9:56 AM
 **/
public interface SmsRepository extends JpaRepository<Sms, UUID> {
}
