package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.GradePostingDto;
import lunna.school.dto.StudentDto;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 11:36 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class GradePostingController {
    final
    GradePostingService gradePostingService;
    final
    UserService userService;
    final
    ExamScheduleService examScheduleService;
    @Autowired
    JwtUtils utils;

    final
    StudentsService studentsService;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    SchoolStreamService schoolStreamService;

    public GradePostingController(GradePostingService gradePostingService, UserService userService,
                                  ExamScheduleService examScheduleService, StudentsService studentsService) {
        this.gradePostingService = gradePostingService;
        this.userService = userService;
        this.examScheduleService = examScheduleService;
        this.studentsService = studentsService;
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/grade_posting/list")
    public ApiResponse getGradePostings(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        List<GradePostingDto> gradePostingDtoList;
        try {
            gradePostingDtoList = gradePostingService.listByOrg(user.getOrganization())
                    .stream()
                    .map(grade -> modelMapper.map(grade, GradePostingDto.class))
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }


        return new ApiResponse(
                gradePostingDtoList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_add_grade_posting\")")
    @PostMapping("/grade_posting/create")
    public ApiResponse createGradePosting(@RequestBody GradePosting gradePosting,
                                          BindingResult result,
                                          Principal principal) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors().toString());
        }
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        Student student = studentsService.getStudentById(gradePosting.getStudent().getStudent_id());
        gradePosting.setClass_model(student.getSchool_stream().getClass_model());
        gradePosting.setPosted_by(user);
        gradePosting.setOrganization(user.getOrganization());
        GradePostingDto gradePosting1 = null;
        try {
            gradePosting1 = modelMapper.map(gradePostingService.saveOrUpdate(gradePosting), GradePostingDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                gradePosting1,
                "Grade posting",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/grade_posting/{grade_posting_id}")
    public ApiResponse getGradePostingById(@PathVariable String grade_posting_id) {
        GradePostingDto gradePosting = modelMapper.map(gradePostingService.getById(UUID.fromString(grade_posting_id)), GradePostingDto.class);
        return new ApiResponse(
                gradePosting,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_edit_grade_posting\")")
    @PutMapping("/grade_posting/update")
    public ApiResponse updateGradePosting(@RequestBody GradePosting gradePosting) {
        GradePosting gradePosting1 = gradePostingService.getById(gradePosting.getGrade_posting_id());
        gradePosting1.setExam_schedule(gradePosting.getExam_schedule());
        gradePosting1.setGrade(gradePosting.getGrade());
        gradePosting1.setClass_model(gradePosting.getClass_model());
        gradePosting1.setStudent(gradePosting.getStudent());
        GradePostingDto gradePosting2 = null;
        try {
            gradePosting2 = modelMapper.map(gradePostingService.saveOrUpdate(gradePosting1), GradePostingDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(
                gradePosting2,
                "Grade posting updated",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_grade_posting\")")
    @DeleteMapping("/grade_posting/{grade_posting_id}/delete")
    public ApiResponse deleteGradePosting(@PathVariable String grade_posting_id) {
        GradePosting gradePosting = gradePostingService.getById(UUID.fromString(grade_posting_id));
        gradePostingService.softDelete(gradePosting);
        return new ApiResponse(
                null,
                "Deleted",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/grade_posting/{exam_schedule_id}/exam_schedule")
    public ApiResponse getPostingByExamSchedule(@PathVariable String exam_schedule_id) {
        ExamSchedule examSchedule = examScheduleService.getById(UUID.fromString(exam_schedule_id));
        List<GradePostingDto> gradePostingList = gradePostingService.listByExamSchedule(examSchedule)
                .stream()
                .map(gradePosting -> modelMapper.map(gradePosting, GradePostingDto.class))
                .collect(Collectors.toList());
        return new ApiResponse(
                gradePostingList,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/student/{student_id}/exams")
    public ApiResponse getStudentGrades(@PathVariable String student_id) {
        Student student = studentsService.getStudentById(UUID.fromString(student_id));
        List<GradePosting> studentExams = null;
        try {
            studentExams = gradePostingService.studentExams(student);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                studentExams,
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/student/{student_id}/exams/{year}/summary")
    public ApiResponse getStudentExamsGraph(@PathVariable String student_id,
                                            @PathVariable String year) {
        int exam_year = 0;
        if (year == null) {
            exam_year = new Date().getYear();
        } else {
            exam_year = Integer.parseInt(year);
        }
        System.out.println("year" + exam_year);
        Student student = studentsService.getStudentById(UUID.fromString(student_id));
        List<GradePosting> studentExams = null;

        return new ApiResponse(
                gradePostingService.studentExamGrades(student, exam_year),
                "",
                HttpStatus.OK.value()
        );
    }

    @PostAuthorize("hasAuthority(\"can_view_grade_posting\")")
    @GetMapping("/students/exams/{exam_id}")
    public ApiResponse getStudentByExam(@PathVariable("exam_id") String exam_id,
                                        HttpServletRequest request) {
        List<StudentDto> students = new ArrayList<>();
        try {
            ExamSchedule examSchedule = examScheduleService.getById(UUID.fromString(exam_id));
            ClassModel classModel = examSchedule.getClass_model();
            if (examSchedule.getClass_model() != null) {
                students = studentsService.getStudentsPerSchoolStream(
                                utils.getSchoolId(request),
                                classModel.getClass_id())
                        .stream()
                        .map(student -> modelMapper.map(student, StudentDto.class))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }


        return new ApiResponse(
                students,
                "",
                HttpStatus.OK.value()
        );
    }

}
