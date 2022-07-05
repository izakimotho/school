package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.ExamScheduleDto;
import lunna.school.dto.StudentDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.ExamSchedule;
import lunna.school.model.User;
import lunna.school.service.ExamScheduleService;
import lunna.school.service.StudentsService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 7:57 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ExamScheduleController {

    final UserService userService;
    ModelMapper modelMapper = new ModelMapper();
    final ExamScheduleService examScheduleService;
    @Autowired
    StudentsService studentsService;
    @Autowired
    public ExamScheduleController(UserService userService, ExamScheduleService examScheduleService) {
        this.userService = userService;
        this.examScheduleService = examScheduleService;
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_schedule\")")
    @GetMapping("/schools/exam_schedule")
    public ApiResponse fetchAllSchoolsExamSchedules() {
        return new ApiResponse(
                examScheduleService.listAllSchedule(),
                "Exam Schedule list",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_schedule\")")
    @GetMapping("/exam_schedule")
    public ApiResponse fetchAllMyExamSchedules(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        List<ExamScheduleDto> examScheduleDto = new ArrayList<>();

        try {
            examScheduleDto =  examScheduleService.fetchAll(user.getOrganization());
        }catch (Exception e){
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
               examScheduleDto,
                "Exam Schedule list",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_exam_schedule\")")
    @PostMapping("/exam_schedule/create")
    public ApiResponse createExamSchedule(@RequestBody ExamSchedule examSchedule,
                                          Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }

        if (examSchedule.getOrganization() == null) {
            examSchedule.setOrganization(user.getOrganization());
        }
        examSchedule.setSchedule_by(user);
        ExamScheduleDto examSchedule1 = null;
        try {
            examSchedule1 = modelMapper.map(examScheduleService.saveOrUpdate(examSchedule), ExamScheduleDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                examSchedule1,
                "Exam Schedule Created successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_schedule\")")
    @GetMapping("/exam_schedule/{exam_schedule_id}")
    public ApiResponse fetchExamScheduleById(@PathVariable String exam_schedule_id) {
        ExamScheduleDto examSchedule = examScheduleService.getByIdExams(UUID.fromString(exam_schedule_id));
        return new ApiResponse(
                examSchedule,
                "Exam Schedule",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_exam_schedule\")")
    @GetMapping("/exam_schedule/class/{class_id}")
    public ApiResponse fetchExamScheduleByClass(@PathVariable String class_id,
                                                Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }

        List<ExamScheduleDto> examSchedule = examScheduleService.fetchByClassOrg(UUID.fromString(class_id), user.getOrganization().getOrg_id());
        return new ApiResponse(
                examSchedule,
                "Exam Schedule",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_exam_schedule\")")
    @PutMapping("/exam_schedule/update")
    public ApiResponse updateExamSchedule(
            Principal principal,
            @RequestBody ExamSchedule examSchedule) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        ExamSchedule examSchedule1 = examScheduleService.getById(examSchedule.getExam_schedule_id());

        examSchedule1.setExam_schedule_name(examSchedule.getExam_schedule_name());
        examSchedule1.setClass_model(examSchedule.getClass_model());
        examSchedule1.setExam_time(examSchedule.getExam_time());
        examSchedule1.setExam_date(examSchedule.getExam_date());
        examSchedule1.setSubject(examSchedule.getSubject());
        examSchedule1.setExam_type(examSchedule.getExam_type());
        return new ApiResponse(
                examScheduleService.saveOrUpdate(examSchedule1),
                "Exam Schedule updated successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_exam_schedule\")")
    @DeleteMapping("/exam_schedule/{exam_schedule_id}/delete")
    public ApiResponse deleteExamSchedule(@PathVariable String exam_schedule_id) {
        ExamSchedule examSchedule = examScheduleService.getById(UUID.fromString(exam_schedule_id));
        examScheduleService.delete(examSchedule);
        return new ApiResponse(
                null
                ,
                "Deleted successfully",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_student\")")
    @GetMapping("/exam_schedule/{exam_schedule_id}/students")
    public ApiResponse getExamStudents(@PathVariable("exam_schedule_id") String exam_schedule_id,
                                       HttpServletRequest request) {
        ExamSchedule examSchedule = examScheduleService.getById(UUID.fromString(exam_schedule_id));
        List<StudentDto> students;
        try {
            students = studentsService.getStudentsPerSchoolStream(examSchedule.getOrganization().getOrg_id(),
                            examSchedule.getClass_model().getClass_id())
                    .stream()
                    .map(student -> modelMapper.map(student, StudentDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }

        return new ApiResponse(
                students,
                "Students",
                HttpStatus.OK.value()
        );
    }
}
