package lunna.school.service;

import lunna.school.model.StreamDetails;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 09. Jul 2021 10:10 AM
 **/

public interface StreamDetailsService extends IService<StreamDetails>{
    List<StreamDetails> getByClassStream(UUID school_stream_id);

    List<StreamDetails> getByStreamSchoolOrgId(UUID school_id);
}
