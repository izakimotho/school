package lunna.school.model;

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
 * Created : 18. Aug 2021 9:33 AM
 **/

@Getter
@Setter
@Entity
@Table(name = "sms")
public class Sms {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "sms_id", updatable = false, nullable = false)
    private UUID school_type_id;
    private String message;
    private String subject;
    private Boolean schedule;
    private Date schedule_date;
    @ElementCollection
    private List<String> recipients;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public Sms(){}


}
