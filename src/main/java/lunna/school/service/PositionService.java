package lunna.school.service;

import lunna.school.model.Positions;
import lunna.school.model.UserType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:17 PM
 **/
public interface PositionService extends IService<Positions> {
    Positions getByName(String position_name);
    List<Positions> getBySchool(UUID organization);
    List<Positions> getPerSchoolAndCategory(UUID organization, UserType category);
    List<Positions> getPerCategory(UserType category);
    List<UserType> getLeadershipTypes();
}
