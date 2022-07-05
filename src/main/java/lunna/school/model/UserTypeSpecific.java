package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * UserTypeSpecific
 *
 * @author Collins K. Sang
 * 2021/07/13 15:06
 **/
@Entity
@Getter
@Setter
@Table(name = "user_types", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_type", "type_name" }) })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserTypeSpecific {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "user_type_id", updatable = false, nullable = false)
    private UUID user_type_id;

    @ManyToOne
    @JoinColumn(name = "user_type")
    private UserType user_type;

    private String type_name;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    private String created_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default NULL")
    private Date updatedAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

}
