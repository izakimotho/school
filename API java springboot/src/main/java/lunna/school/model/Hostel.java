package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * Hostel
 *
 * @author Collins K. Sang
 * 2021/07/02 11:08
 **/

@Entity
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hostel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "hostel_id", updatable = false, nullable = false)
    private UUID hostel_id;

    private String hostel_name;

    private Integer hostel_capacity;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization org_id;

    @OneToOne
    @JoinColumn(name = "hostel_captain")
    private Student hostel_captain;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "hostel_master")
    private Staff hostel_master;

    private String created_by;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date created_at = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;


    public Hostel() {
    }

    public Hostel(String hostel_name, Integer hostel_capacity, String description, Organization org_id, Student hostel_captain, Staff hostel_master, String created_by, Date created_at) {
        this.hostel_name = hostel_name;
        this.hostel_capacity = hostel_capacity;
        this.description = description;
        this.org_id = org_id;
        this.hostel_captain = hostel_captain;
        this.hostel_master = hostel_master;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Hostel(UUID hostel_id, String hostel_name, Integer hostel_capacity, String description, Organization org_id, Student hostel_captain, Staff hostel_master, String created_by, Date created_at) {
        this.hostel_id = hostel_id;
        this.hostel_name = hostel_name;
        this.hostel_capacity = hostel_capacity;
        this.description = description;
        this.org_id = org_id;
        this.hostel_captain = hostel_captain;
        this.hostel_master = hostel_master;
        this.created_by = created_by;
        this.created_at = created_at;
    }
}
