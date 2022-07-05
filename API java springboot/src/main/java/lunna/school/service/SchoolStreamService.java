package lunna.school.service;

import lunna.school.model.SchoolStream;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 2:28 PM
 **/
@Service
public interface SchoolStreamService extends IService<SchoolStream> {
    void softDelete(SchoolStream schoolStream);
    SchoolStream getByName(String stream_name);

    List<SchoolStream> listBySchoolId(UUID school_id);
    SchoolStream schoolStreamUpdate(SchoolStream schoolStream);

}
