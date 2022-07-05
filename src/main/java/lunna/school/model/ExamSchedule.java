package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 7:38 AM
 **/
@Entity
@Getter
@Setter
@ToString
@Table(name = "exam_schedules")
@Validated
public class ExamSchedule {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "exam_schedule_id", updatable = false, nullable = false)
    private UUID exam_schedule_id;

    @NotEmpty(message = "Schedule name should not be null")
    private String exam_schedule_name;

    @ManyToOne
    @JoinColumn(name = "exam_type_id")
    private ExamType exam_type;

    @ManyToOne
    @JoinColumn(name = "school_subject_id")
    private SchoolSubject subject;

    @ManyToOne
    @JoinColumn(name = "terms")
    private Terms terms;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "TIMESTAMP")
    private Date exam_date;
    @Column(name = "exam_time", columnDefinition = "TIME")
    private LocalTime exam_time;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassModel class_model;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User schedule_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;
    public ExamSchedule(){}

}
