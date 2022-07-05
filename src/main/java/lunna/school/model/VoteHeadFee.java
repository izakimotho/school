package lunna.school.model;

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
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 6/15/22, Wednesday
 **/

@Getter
@Setter
@ToString
@Entity
@Table(name = "vote_head_fees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fee_structure_id", "vote_head_id", "term_details_id"})
})
@Validated
public class VoteHeadFee {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "vote_head_fee_id", updatable = false, nullable = false)
    private UUID vote_head_fee_id;

    @ManyToOne
    @JoinColumn(name = "fee_structure_id")
    private FeeStructure feeStructure;

    @ManyToOne
    @JoinColumn(name = "vote_head_id")
    private VoteHead voteHead;

    @ManyToOne
    @JoinColumn(name = "term_details_id")
    private TermDetails termDetails;

    @Column(columnDefinition = "real default 0.0")
    private double amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;
}
