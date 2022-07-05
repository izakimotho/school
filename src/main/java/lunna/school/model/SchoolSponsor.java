package lunna.school.model;

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
 * Created : 30. Jun 2021 3:06 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "school_sponsor")
public class SchoolSponsor {

    /**
     * @doc
     * Private,
     * DEB,
     * Roman
     * Catholic
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "school_sponsor_id", updatable = false, nullable = false)
    private UUID school_sponsor_id;
    private String school_sponsor_name;
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public SchoolSponsor(){}
}
