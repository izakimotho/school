package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Collins K. Sang
 * 5/18/22 3:13 PM
 * school
 * FeeStructure
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "fee_structure")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FeeStructure {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "fee_structure_id", updatable = false, nullable = false)
    private UUID fee_structure_id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @NotNull(message = "Class is mandatory")
    private ClassModel class_model;


    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    @NotNull(message = "academic_year is mandatory")
    private AcademicYear academic_year;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VoteHeadFee> voteHeadFees = new ArrayList<>();


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
