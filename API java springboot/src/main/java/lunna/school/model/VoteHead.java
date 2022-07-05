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
 * Collins K. Sang
 * 5/18/22 9:00 AM
 * school
 * FeeVoteHead
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_fee_vote_heads")
@Validated
public class VoteHead {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "fee_vote_id", updatable = false, nullable = false)
    private UUID fee_vote_id;

    private String vote_head_name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organization")
    @NotNull(message = "org_id is mandatory")
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
