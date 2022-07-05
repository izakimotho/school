package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.GenderEnum;
import lunna.school.model.Hostel;
import lunna.school.model.Positions;

import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StudentDto
 *
 * @author Collins K. Sang
 * 2021/06/29 11:12
 **/
@Getter
@Setter
@ToString
public class StudentDto {
    private UUID student_id;
    private String first_name;

    private String middle_name;

    private String surname;

    private GenderEnum gender;

    private String dob;

    private String guardians_name;

    private long guardian_phone;

    private String next_of_kin;

    private long kin_phone;

    private String kin_relationship;

    private boolean is_boarder;

    private Date admission_date;

    private String photo;

    private String birth_certificate;

    private String fees;
    private SchoolStreamDto school_stream;
    private Hostel hostel;

    private Positions positions;

    public StudentDto() {
    }

    public StudentDto(String first_name, String middle_name, String surname,
                      GenderEnum gender, String dob, String guardians_name,
                      long guardian_phone, String next_of_kin,
                      long kin_phone, String kin_relationship, boolean is_boarder,
                      Date admission_date, String photo, String birth_certificate,
                      SchoolStreamDto school_stream, Hostel hostel_name, String fees) {

        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.guardians_name = guardians_name;
        this.guardian_phone = guardian_phone;
        this.next_of_kin = next_of_kin;
        this.kin_phone = kin_phone;
        this.kin_relationship = kin_relationship;
        this.is_boarder = is_boarder;
        this.admission_date = admission_date;
        this.photo = photo;
        this.birth_certificate = birth_certificate;
        this.school_stream = school_stream;
        this.hostel = hostel_name;
        this.fees = fees;
    }

    public StudentDto(UUID student_id, String first_name, String middle_name, String surname,
                      GenderEnum gender, String dob, String guardians_name, long guardian_phone,
                      String next_of_kin,
                      long kin_phone, String kin_relationship,
                      boolean is_boarder, Date admission_date,
                      String photo, String birth_certificate, SchoolStreamDto school_stream,
                      Hostel hostel, String fees) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.guardians_name = guardians_name;
        this.guardian_phone = guardian_phone;
        this.next_of_kin = next_of_kin;
        this.kin_phone = kin_phone;
        this.kin_relationship = kin_relationship;
        this.is_boarder = is_boarder;
        this.admission_date = admission_date;
        this.photo = photo;
        this.birth_certificate = birth_certificate;
        this.school_stream = school_stream;
        this.hostel = hostel;
        this.fees = fees;
    }
}
