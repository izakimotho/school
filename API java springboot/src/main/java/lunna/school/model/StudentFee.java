package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/16/22 9:12 AM
 * school
 * StudentFeeController
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "student_fees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentFee {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "student_fee_id", updatable = false, nullable = false)
    private UUID student_fee_id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @NotNull(message = "Student_id is mandatory")
    private Student student_id;
    @ManyToOne
    @JoinColumn(name = "org_id")
    @NotNull(message = "org_id is mandatory")
    private Organization org_id;
    @ManyToOne
    @JoinColumn(name = "class_id")
    @NotNull(message = "class_id is mandatory")
    private ClassModel class_id;
    @ManyToOne
    @JoinColumn(name = "school_stream_id")
    @NotNull(message = "school_stream_id is mandatory")
    private SchoolStream school_stream_id;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    @NotNull(message = "academic_year_id is mandatory")
    private AcademicYear academic_year;

    @ManyToOne
    @JoinColumn(name = "terms")
    private TermDetails terms;

    private Float fee_amount;

    private Float balance;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User created_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public StudentFee(UUID student_fee_id, Student student_id, Organization org_id, ClassModel class_id, SchoolStream school_stream_id, AcademicYear year, TermDetails terms, Float fee_amount, Float balance, User created_by) {
        this.student_fee_id = student_fee_id;
        this.student_id = student_id;
        this.org_id = org_id;
        this.class_id = class_id;
        this.school_stream_id = school_stream_id;
        this.academic_year = year;
        this.terms = terms;
        this.fee_amount = fee_amount;
        this.balance = balance;
        this.created_by = created_by;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public StudentFee() {

    }
}
