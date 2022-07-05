package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 08. Jul 2021 3:56 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "stream_details")
public class StreamDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "stream_detail_id", updatable = false, nullable = false)
    private UUID stream_detail_id;

    @ManyToOne
    @JoinColumn(name = "school_stream_id")
    @JsonBackReference
    private  SchoolStream school_stream;

    @ManyToOne
    @JoinColumn(name = "school_subject_id")
    private  SchoolSubject school_subject;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private  Staff staff;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public StreamDetails(){}




}
