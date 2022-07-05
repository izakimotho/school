package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 2:35 PM
 **/

@Getter
@Setter
@Entity
@Table(name = "school_level")
public class SchoolLevel {
    /**
     * Secondary School
     * Primary School
     * Kindergarten School
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "school_level_id", updatable = false, nullable = false)
    private UUID school_level_id;
    private String school_level_name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "school_level", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Organization> school;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public SchoolLevel() {
    }
}
