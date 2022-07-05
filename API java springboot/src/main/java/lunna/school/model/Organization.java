package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 3:27 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "organization")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "org_id", updatable = false, nullable = false)
    private UUID org_id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "org_name",unique = true)
    private String org_name;

    @NotBlank(message = "Code is mandatory")
    @Column(name = "code",unique = true)
    private String code;

    private String motto;
    private String logo;
    @Email(message = "Provide a valid email address")
    @NotEmpty(message = "*Please provide an email")
    private String email_address;
    @ElementCollection
    private List<String> phone_numbers;

    @ElementCollection
    private List<String> mobile_phone_numbers;

    private String postal_address;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="parent_school_id")
    private Organization parentSchool;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentSchool")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Organization> children = new HashSet<Organization>();


    @OneToOne
    @JoinColumn(name="school_type_id", nullable=true)
    private SchoolType school_type;

    @ManyToOne
    @JoinColumn(name="county_id", nullable=true)
    private County county;

    @ManyToOne
    @JoinColumn(name="sub_county_id", nullable=true)
    private SubCounty sub_county;

    @ManyToOne
    @JoinColumn(name="ward_id", nullable=true)
    private Ward ward;

    @ManyToOne
    @JoinColumn(name="school_level_id", nullable=true)
    private SchoolLevel school_level;

    @ManyToOne
    @JoinColumn(name="school_cluster_id", nullable=true)
    private SchoolCluster school_cluster;

    @ManyToOne
    @JoinColumn(name="education_system_id", nullable=true)
    private EducationSystem education_system;

    @ManyToOne
    @JoinColumn(name="school_category_id", nullable=true)
    private SchoolCategory school_category;

    @ManyToOne
    @JoinColumn(name="school_gender_id", nullable=true)
    private SchoolGender school_gender;

    @ManyToOne
    @JoinColumn(name="school_sponsor_id", nullable=true)
    private SchoolSponsor school_sponsor;

    @Column(columnDefinition = "TEXT")
    private String school_history;


    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public Organization() {}

    public Organization(String org_name) {
        this.org_name = org_name;
    }
}
