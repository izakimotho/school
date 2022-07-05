package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 2:29 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "education_system")
public class EducationSystem {
    /**
     * @doc
     * CBC,
     * IGCSE
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "education_system_id", updatable = false, nullable = false)
    private UUID education_system_id;
    @Column(unique = true)
    @NotBlank
    @NotEmpty(message = "*Please provide Education system name")
    private String education_system_name;
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public EducationSystem(){}

}
