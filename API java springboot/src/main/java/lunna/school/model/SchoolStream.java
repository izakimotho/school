package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 02. Jul 2021 3:51 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "school_streams")
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolStream {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "school_stream_id", updatable = false, nullable = false)
    private UUID school_stream_id;

    @NotEmpty(message = "Stream name should not be empty")
    @NotNull(message = "Stream name should not be null")
    private String stream_name;

    @Column(nullable = true)
    private String abbr;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @NotNull(message = "Class is mandatory")
    private ClassModel class_model;

    @NotNull(message = "Class Capacity is mandatory")
    private Integer class_capacity;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student class_rep;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff class_teacher;

    @OneToMany(mappedBy="school_stream")
    public List<Student> studentList = new ArrayList<Student>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "stream_details",
            joinColumns = @JoinColumn(name = "school_stream_id"),
            inverseJoinColumns = @JoinColumn(name = "stream_detail_id"))
    public List<StreamDetails> class_subjects;


    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public SchoolStream() {
    }

}
