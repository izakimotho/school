package lunna.school.controller;

import lunna.school.dto.*;
import lunna.school.exception.BadRequestException;
import lunna.school.model.*;
import lunna.school.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/16/22 9:07 AM
 * school
 * StudentFeeController
 * IntelliJ IDEA
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StudentFeeController {
    final StudentFeeService studentFeeService;
    final UserService userService;
    final FeeService feeService;
    final TermDetailsService termDetailsService;

    final StudentsService studentsService;
    final FeeStructureService feeStructureService;

    ModelMapper modelMapper = new ModelMapper();
    ModelMapper modelMapper2 = new ModelMapper();

    @Autowired
    public StudentFeeController(StudentFeeService studentFeeService, UserService userService, FeeService feeService, TermDetailsService termDetailsService, StudentsService studentsService, FeeStructureService feeStructureService) {
        this.studentFeeService = studentFeeService;
        this.userService = userService;
        this.feeService = feeService;
        this.termDetailsService = termDetailsService;
        this.studentsService = studentsService;
        this.feeStructureService = feeStructureService;
    }

    //create student fee
    @PostAuthorize("hasAuthority(\"can_add_student_fee\")")
    @PostMapping("/student-fees")
    public ApiResponse createFee(@RequestBody StudentFee studentFee, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        studentFee.setCreated_by(user);
        TermDetails termDetails = termDetailsService.getById(studentFee.getTerms().getTerm_details_id());

        List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndYear(
                termDetails.getAcademic_year().getAcademic_year_id(),
                studentFee.getOrg_id().getOrg_id(),
                studentFee.getClass_id().getClass_id()
        );
        double sum = 0.0;
        for (FeeStructure structure : feeStructure) {
            sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
        }

        float fee_amount = (float) sum;
        float balance;
        float paid_amount;
        float total_paid;

        List<StudentFee> studentFee1 = studentFeeService.getFeeByTermByStudent(studentFee.getTerms().getTerm_details_id(), studentFee.getStudent_id().getStudent_id());
        if (studentFee1.size() == 0) {
            balance = fee_amount - studentFee.getFee_amount();
        } else {
            paid_amount = studentFeeService.getFeeSumByTermByStudent(studentFee.getTerms().getTerm_details_id(), studentFee.getStudent_id().getStudent_id());
            total_paid = paid_amount + studentFee.getFee_amount();
            balance = fee_amount - total_paid;
        }
        studentFee.setBalance(balance);

        Student student = studentsService.getStudentById(studentFee.getStudent_id().getStudent_id());
        SchoolStream schoolStream = student.getSchool_stream();
        studentFee.setSchool_stream_id(schoolStream);
        studentFee.setAcademic_year(termDetails.getAcademic_year());
        StudentFee studentFee2;
        StudentFeeDto studentFeeDto;
        try {
            studentFee2 = studentFeeService.saveOrUpdate(studentFee);
            studentFeeDto = modelMapper.map(studentFee2, StudentFeeDto.class);
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(studentFeeDto, "Student Fee saved successfully!", HttpStatus.OK.value());
    }

    //update student fee
    @PostAuthorize("hasAuthority(\"can_edit_student_fee\")")
    @PutMapping("/student-fees")
    public ApiResponse createFee(@RequestBody StudentFee studentFee) {
        StudentFee studentFee2;
        TermDetails termDetails = termDetailsService.getById(studentFee.getTerms().getTerm_details_id());

        List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndYear(
                termDetails.getAcademic_year().getAcademic_year_id(),
                studentFee.getOrg_id().getOrg_id(),
                studentFee.getClass_id().getClass_id()
        );
        float sum = 0.0F;
        for (FeeStructure structure : feeStructure) {
            sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
        }

        float fee_amount = sum;
        float balance;
        float paid_amount;
        float total_paid;

        List<StudentFee> studentFee1 = studentFeeService.getFeeByTermByStudent(studentFee.getTerms().getTerm_details_id(), studentFee.getStudent_id().getStudent_id());
        if (studentFee1.size() == 0) {
            balance = fee_amount - studentFee.getFee_amount();
        } else {
            paid_amount = studentFeeService.getFeeSumByTermByStudent(studentFee.getTerms().getTerm_details_id(), studentFee.getStudent_id().getStudent_id());
            total_paid = paid_amount + studentFee.getFee_amount();
            balance = fee_amount - total_paid;
        }
        studentFee.setBalance(balance);
        studentFee.setAcademic_year(termDetails.getAcademic_year());

        try {
            studentFee2 = studentFeeService.saveOrUpdate(studentFee);
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(studentFee2, "Student Fee updated successfully!", HttpStatus.OK.value());
    }


    //get all student fees per school
    @PostAuthorize("hasAuthority(\"can_view_student_fee\")")
    @GetMapping("/student-fees")
    public ApiResponse listStudentFee(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        RoleDto roleDto = modelMapper2.map(userDto.getRoles(), RoleDto.class);

        if (Objects.equals(roleDto.getRole_name(), "Super Admin")) {
            return new ApiResponse(modelMapper.map(studentFeeService.listAll(), StudentFeeDto.class), "", HttpStatus.OK.value());
        } else {

            List<FeeRecordDto> feeRecordDto = studentFeeService.ListPerSchool(user.getOrganization().getOrg_id());

            FeeRecordDto feeRecordDto3;
            List<FeeListDto> feeListDto = new ArrayList<>();
            for (FeeRecordDto recordDto : feeRecordDto) {
                TermDetails terms = modelMapper.map(recordDto.getTerms(), TermDetails.class);
                ClassModel classModel = recordDto.getClass_model();
                Student student = recordDto.getStudent_id();
                double paid_amount = recordDto.getPaid_amount();
                SchoolStream school_stream = recordDto.getSchool_stream();
                AcademicYear year = modelMapper.map(recordDto.getAcademic_year(), AcademicYear.class);

                List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndTerm(
                        recordDto.getAcademic_year().getAcademic_year_id(),
                        terms.getTerms().getTerm_id(),
                        recordDto.getStudent_id().getOrganization().getOrg_id(),
                        classModel.getClass_id()
                );

                float sum = 0.0F;
                for (FeeStructure structure : feeStructure) {
                    sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
                }

                float fee_amount = sum;

                double balance = fee_amount - paid_amount;
                feeRecordDto3 = new FeeRecordDto(student, paid_amount, balance, fee_amount, terms, classModel,
                        school_stream, year);
                feeListDto.add(modelMapper.map(feeRecordDto3, FeeListDto.class));

            }
            return new ApiResponse(feeListDto, "Student Fee List", HttpStatus.OK.value());
        }
    }

    //get student fee
    @PostAuthorize("hasAuthority(\"can_view_student_fee\")")
    @GetMapping("/student-fees/student/{student_id}")
    public ApiResponse studentFee(@PathVariable("student_id") String student_id) {
        List<StudentFee> studentFee = studentFeeService.getFeeByStudent(UUID.fromString(student_id));
        List<FeeListDtos> feeListDtos = studentFee
                .stream()
                .map(
                        studentFee1 -> modelMapper.map(studentFee1, FeeListDtos.class))
                .collect(Collectors.toList());
//        FeeListDtos
        return new ApiResponse(feeListDtos, "Student Fee", HttpStatus.OK.value());
    }

    //get student fee per class
    @PostAuthorize("hasAuthority(\"can_view_student_fee\")")
    @GetMapping("/student-fees/class/{class_id}")
    public ApiResponse classFee(@PathVariable("class_id") String class_id) {
        List<FeeRecordDto> studentFee = studentFeeService.getFeeByClass(UUID.fromString(class_id));
        FeeRecordDto feeRecordDtos;
        List<FeeListDto> feeListDto = new ArrayList<>();
        for (FeeRecordDto feeRecordDto : studentFee) {
            TermDetails terms = modelMapper.map(feeRecordDto.getTerms(), TermDetails.class);
            ClassModel classModel = feeRecordDto.getClass_model();
            Student student = feeRecordDto.getStudent_id();
            double paid_amount = feeRecordDto.getPaid_amount();
            SchoolStream school_stream = feeRecordDto.getSchool_stream();
            AcademicYear year = modelMapper.map(feeRecordDto.getAcademic_year(), AcademicYear.class);

            List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndTerm(
                    feeRecordDto.getAcademic_year().getAcademic_year_id(),
                    terms.getTerms().getTerm_id(),
                    feeRecordDto.getStudent_id().getOrganization().getOrg_id(),
                    classModel.getClass_id()
            );
            float sum = 0.0F;
            for (FeeStructure structure : feeStructure) {
                sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
            }

            float fee_amount = sum;
            double balance = fee_amount - paid_amount;
            feeRecordDtos = new FeeRecordDto(student, paid_amount, balance, fee_amount, terms, classModel, school_stream, year);
            feeListDto.add(modelMapper.map(feeRecordDtos, FeeListDto.class));

        }

        return new ApiResponse(feeListDto, "Class Fee", HttpStatus.OK.value());
    }

    //get student fee per term & per school
    @PostAuthorize("hasAuthority(\"can_view_student_fee\")")
    @GetMapping("/student-fees/term/{term}/school/{org_id}")
    public ApiResponse schoolTermFee(@PathVariable("term") String termss, @PathVariable("org_id") String org_id) {
        List<FeeRecordDto> studentFee = studentFeeService.getFeeByTermBySchool(UUID.fromString(termss), UUID.fromString(org_id));

        FeeRecordDto feeRecordDtos;
        List<FeeListDto> feeListDto = new ArrayList<>();
        for (FeeRecordDto feeRecordDto : studentFee) {
            TermDetails terms = modelMapper.map(feeRecordDto.getTerms(), TermDetails.class);
            ClassModel classModel = feeRecordDto.getClass_model();
            Student student = feeRecordDto.getStudent_id();
            double paid_amount = feeRecordDto.getPaid_amount();
            SchoolStream school_stream = feeRecordDto.getSchool_stream();
            AcademicYear year = modelMapper.map(feeRecordDto.getAcademic_year(), AcademicYear.class);

            List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndTerm(
                    feeRecordDto.getAcademic_year().getAcademic_year_id(),
                    terms.getTerms().getTerm_id(),
                    feeRecordDto.getStudent_id().getOrganization().getOrg_id(),
                    classModel.getClass_id()
            );
            float sum = 0.0F;
            for (FeeStructure structure : feeStructure) {
                sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
            }

            float fee_amount = sum;
            double balance = fee_amount - paid_amount;
            feeRecordDtos = new FeeRecordDto(student, paid_amount, balance, fee_amount, terms, classModel, school_stream, year);
            feeListDto.add(modelMapper.map(feeRecordDtos, FeeListDto.class));

        }
        return new ApiResponse(feeListDto,
                "School Term Fee",
                HttpStatus.OK.value());
    }

    //get student fee per term & per class
    @PostAuthorize("hasAuthority(\"can_view_student_fee\")")
    @GetMapping("/student-fees/term/{termId}/class/{class_id}")
    public ApiResponse classTermFee(@PathVariable("term") String termId, @PathVariable("class_id") String class_id) {
        List<FeeRecordDto> studentFee = studentFeeService.getFeeByTermByClass(UUID.fromString(termId), UUID.fromString(class_id));

        FeeRecordDto feeRecordDtos;
        List<FeeListDto> feeListDto = new ArrayList<>();
        for (FeeRecordDto feeRecordDto : studentFee) {
            TermDetails terms = modelMapper.map(feeRecordDto.getTerms(), TermDetails.class);
            ClassModel classModel = feeRecordDto.getClass_model();
            Student student = feeRecordDto.getStudent_id();
            double paid_amount = feeRecordDto.getPaid_amount();
            SchoolStream school_stream = feeRecordDto.getSchool_stream();
            AcademicYear year = modelMapper.map(feeRecordDto.getAcademic_year(), AcademicYear.class);

            List<FeeStructure> feeStructure = feeStructureService.findBySchoolIdAndClassAndTerm(
                    feeRecordDto.getAcademic_year().getAcademic_year_id(),
                    terms.getTerms().getTerm_id(),
                    feeRecordDto.getStudent_id().getOrganization().getOrg_id(),
                    classModel.getClass_id()
            );
            float sum = 0.0F;
            for (FeeStructure structure : feeStructure) {
                sum += structure.getVoteHeadFees().stream().mapToDouble(VoteHeadFee::getAmount).sum();
            }

            float fee_amount = sum;
            double balance = fee_amount - paid_amount;

            feeRecordDtos = new FeeRecordDto(student, paid_amount, balance, fee_amount, terms, classModel, school_stream, year);
            feeListDto.add(modelMapper.map(feeRecordDtos, FeeListDto.class));

        }

        return new ApiResponse(feeListDto,
                "Class Term Fee",
                HttpStatus.OK.value());
    }

    //delete student fee
    @PostAuthorize("hasAuthority(\"can_delete_student_fee\")")
    @DeleteMapping("/student-fees/student/{student_id}")
    public ApiResponse deleteStudentFee(@PathVariable("student_id") String student_id) {
        List<StudentFee> studentFee = studentFeeService.getFeeByStudent(UUID.fromString(student_id));
        studentFeeService.deleteByStudent(studentFee);
        return new ApiResponse(studentFee, "Deleted successfully", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_student_fee\")")
    @DeleteMapping("/student-fees/class/{class_id}")
    public ApiResponse deleteClassFee(@PathVariable("class_id") String class_id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Student> student = studentsService.getStudentsPerSchoolStream(user.getOrganization().getOrg_id(), UUID.fromString(class_id));
        List<FeeRecordDto> studentFee = new ArrayList<>();
        for (Student student1 : student) {
            studentFee = studentFeeService.getFeeByClass(student1.getSchool_stream().getClass_model().getClass_id());
        }
        studentFeeService.deleteByClass(UUID.fromString(class_id));

        return new ApiResponse(null, "Deleted successfully", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_delete_student_fee\")")
    @DeleteMapping("/student-fees/school/{school_id}")
    public ApiResponse deleteSchoolFee(@PathVariable("school_id") String school_id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Student> student = studentsService.getStudentsPerSchool(UUID.fromString(school_id));
        List<FeeRecordDto> studentFee;
        studentFee = studentFeeService.ListPerSchool(UUID.fromString(school_id));
        studentFeeService.deleteBySchool(UUID.fromString(school_id));

        return new ApiResponse(null, "Deleted successfully", HttpStatus.OK.value());
    }
}
