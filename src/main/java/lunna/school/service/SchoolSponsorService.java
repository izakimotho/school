package lunna.school.service;

import lunna.school.model.SchoolSponsor;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:21 PM
 **/
public interface SchoolSponsorService extends IService<SchoolSponsor> {
    SchoolSponsor getByName(String school_sponsor_name);
}
