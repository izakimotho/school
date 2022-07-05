package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 15. Jul 2021 8:44 AM
 **/
@Entity
@Getter
@Setter
@ToString
@Table(name = "grade_postings")

public class GradePosting {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "grade_posting_id", updatable = false, nullable = false)
    private UUID grade_posting_id;

    @JsonIgnoreProperties(value = { "organization" })
    @ManyToOne
    @JoinColumn(name = "exam_schedule_id")
    private ExamSchedule exam_schedule;

    @JsonIgnoreProperties(value = { "education_system" })
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassModel class_model;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private float grade;
    private String remarks;

    @JsonIgnoreProperties(value = { "organization","user_category" })
    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User posted_by;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

}
