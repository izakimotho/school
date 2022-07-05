package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * Student
 *
 * @author Collins K. Sang
 * 2021/06/29 09:05
 **/
@Getter
@Setter
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "student_id", updatable = false, nullable = false)
    private UUID student_id;

    @NotNull(message = "Student Firstname required")
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "middle_name")
    private String middle_name;

    @NotNull(message = "Student Surname required")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull(message = "Student Gender required")
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @Column(name = "dob")
    private String dob;
    @NotNull(message = "Student Guardian name required")
    @Column(name = "guardians_name", nullable = false)
    private String guardians_name;

    @NotNull(message = "Student Guardian phone required")
    @Column(name = "guardian_phone", nullable = false)
    private long guardian_phone;

    @Column(name = "next_of_kin")
    private String next_of_kin;

    @Column(name = "kin_phone")
    private long kin_phone;

    @Column(name = "kin_relationship")
    private String kin_relationship;

    @Column(name = "is_boarder", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean is_boarder;

    @NotNull(message = "Admission Date required")
    @Column(name = "admission_date")
    private Date admission_date;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birth_certificate")
    private String birth_certificate;

    @NotNull(message = "School Stream should not be Empty")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "school_stream_id")
    @JsonBackReference(value = "school-stream")
    private SchoolStream school_stream;
    @OneToOne
    @JoinColumn(name = "hostel_name")
    @JsonBackReference(value = "hotel")
    private Hostel hostel;
    @Column(name = "fees")
    private String fees;
    @ManyToOne
    @JoinColumn(name = "position_held")
    private Positions positions;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User created_by;

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_adm_number_seq")
    @GeneratorType(type = AdmissionGenerator.class, when = GenerationTime.INSERT)
    @Column(name = "adm_number", unique = true, nullable = false, updatable = false)
    private String adm_number;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public Student() {
    }

    public Student(UUID student_id, String first_name, String middle_name, String surname, GenderEnum gender,
                   String dob, String guardians_name, long guardian_phone, String next_of_kin, long kin_phone,
                   String kin_relationship, boolean is_boarder, Date admission_date, String avatar,
                   String birth_certificate, SchoolStream school_stream, Hostel hostel, String fees,
                   Positions positions, User created_by) {
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
        this.avatar = avatar;
        this.birth_certificate = birth_certificate;
        this.school_stream = school_stream;
        this.hostel = hostel;
        this.fees = fees;
        this.positions = positions;
        this.created_by = created_by;
    }
}
