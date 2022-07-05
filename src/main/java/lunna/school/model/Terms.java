package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jul 2021 11:31 AM
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_terms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Validated
public class Terms {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "term_id", updatable = false, nullable = false)
    private UUID term_id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "org_id")
    @NotNull(message = "organization is mandatory")
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

}
