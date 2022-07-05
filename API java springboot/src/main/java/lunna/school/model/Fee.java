package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/13/22 10:58 AM
 * school
 * Fee
 * IntelliJ IDEA
 **/
@Getter
@Setter
@Entity
@Table(name = "fees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fee {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "fees_id", updatable = false, nullable = false)
    private UUID fees_id;

    @ManyToOne
    @JoinColumn(name = "terms")
    private Terms terms;

    private Float fee_amount;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @NotNull(message = "Class is mandatory")
    private ClassModel class_model;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User created_by;

    private int year;


    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();


    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;
}
