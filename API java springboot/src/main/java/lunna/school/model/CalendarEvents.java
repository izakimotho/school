package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 07. Jul 2021 11:09 AM
 **/
@Getter
@Setter
@Entity
@Table(name = "calendar_events")
public class CalendarEvents {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "calendar_event_id", updatable = false, nullable = false)
    private UUID calendar_event_id;

    @NotBlank
    @NotEmpty(message = "*Please provide calendar event")
    private String calendar_event;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date event_date;

    @ManyToOne
    @JoinColumn(name = "calendar_item_id")
    private CalendarItem calendar_item;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;


}
