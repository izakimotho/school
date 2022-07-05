package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * ClassModel
 *
 * @author Collins K. Sang
 * 2021/07/02 13:16
 **/
@Entity
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ClassModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "class_id", updatable = false, nullable = false)
    private UUID class_id;

    @NotEmpty(message = "Class name must not be empty")
    private String class_name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "education_system_id")
    @NotNull(message = "Education System must not be null")
    private EducationSystem education_system;

    @ManyToOne
    @JoinColumn(name = "school_level_id")
    @NotNull(message = "School Level must not be null")
    private SchoolLevel school_level;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public ClassModel() {
    }

    public ClassModel(UUID class_id, String class_name, String description, Date createAt) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.description = description;
        this.createAt = createAt;
    }

    public ClassModel(UUID class_id, String class_name, String description, EducationSystem educationSystem, SchoolLevel schoolLevel, Date createAt) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.description = description;
        this.education_system = educationSystem;
        this.school_level = schoolLevel;
        this.createAt = createAt;
    }
}
