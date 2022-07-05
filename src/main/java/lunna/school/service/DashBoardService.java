package lunna.school.service;

import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
public interface DashBoardService {
    Long getUsers(UUID orgId);
    Long getStaffs(UUID orgId);
    Long getStudents(UUID orgId);
    Long getClasses(UUID orgId);
    Long getSubjects(UUID orgId);
}
