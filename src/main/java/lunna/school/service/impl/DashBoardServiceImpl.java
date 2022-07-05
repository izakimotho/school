package lunna.school.service.impl;

import lunna.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
@Service
public class DashBoardServiceImpl implements DashBoardService {
    @Autowired
    UserService userService;
    @Autowired
    StaffService staffService;

    @Autowired
    SchoolStreamService schoolStreamService;

    @Autowired
    SchoolSubjectService schoolSubjectService;

    @Autowired
    StudentsService studentsService;



    @Override
    public Long getUsers(UUID orgId) {
        return userService.count(orgId);
    }

    @Override
    public Long getStaffs(UUID orgId) {
        return staffService.count(orgId);
    }

    @Override
    public Long getStudents(UUID orgId) {
        return studentsService.count(orgId);
    }

    @Override
    public Long getClasses(UUID orgId) {
        return schoolStreamService.count(orgId);
    }

    @Override
    public Long getSubjects(UUID orgId) {
        return schoolSubjectService.count(orgId);
    }
}
