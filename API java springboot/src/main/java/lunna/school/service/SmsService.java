package lunna.school.service;

import lunna.school.model.Sms;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 18. Aug 2021 9:58 AM
 **/
public interface SmsService extends IService<Sms>{
    List<String> recipient(UUID school_id);
}
