package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * Positions
 *
 * @author Collins K. Sang
 * 2021/07/08 14:06
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "leadership_positions")
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Positions implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "position_id", updatable = false, nullable = false)
    private UUID position_id;
    private String position_name;
    @OneToOne
    @JoinColumn(name = "position_holder")
    private UserType position_holder;
    private String description;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User created_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public Positions() {
    }

    public Positions(UUID position_id, String position_name, UserType position_holder, String description, Organization organization, User user) {
        this.position_id = position_id;
        this.position_name = position_name;
        this.position_holder = position_holder;
        this.description = description;
        this.organization = organization;
        this.created_by = user;
    }
}
