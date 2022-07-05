package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 2:06 PM
 * school
 * TermDetails
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "term_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TermDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "term_details_id", updatable = false, nullable = false)
    private UUID term_details_id;
    @ManyToOne
    @JoinColumn(name = "terms")
    @NotNull(message = "terms is mandatory")
    private Terms terms;
    private Date date_from;
    private Date date_to;
    private String term_description;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "academic_year")
    private AcademicYear academic_year;
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
