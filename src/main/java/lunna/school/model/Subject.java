package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 10:47 AM
 **/
@Getter
@Setter
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "subject_id", updatable = false, nullable = false)
    private UUID subject_id;
    @NotEmpty(message = "Subject should not be null")
    private String subject_name;
    private String subject_abbr;
    @ManyToOne
    @JoinColumn(name = "school_level_id")
    private SchoolLevel school_level;

    @ManyToOne
    @JoinColumn(name = "education_system_id")
    private EducationSystem education_system;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;


    private String description;

    public Subject() {
    }

}
