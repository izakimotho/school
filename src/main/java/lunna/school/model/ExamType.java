package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 13. Jul 2021 4:22 PM
 **/
@Entity
@Getter
@Setter
@ToString
@Table(name = "exam_types")
@Validated
public class ExamType {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "exam_type_id", updatable = false, nullable = false)
    private UUID exam_type_id;

    @NotEmpty(message = "Exam type name should not be empty")
    private String exam_type_name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;
    public ExamType(){}
}
