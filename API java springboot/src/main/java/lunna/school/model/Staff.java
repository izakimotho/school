package lunna.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 3:27 PM
 **/

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Staff {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    @Column(name = "staff_id", updatable = false, nullable = false)
    private UUID staff_id;
    @OneToOne
    @JoinColumn(name = "user_type_id")
    private UserType type;
    private String first_name;
    private String middle_name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Transient
    private String password;
    private GenderEnum gender;
    private String title;
    private String phone_number;
    private String marital_status;
    private String spouse_name;
    private String spouse_phone;
    private String avatar;
    private Date employment_date;
    private String created_by;

    @Transient
    private UUID school;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "position_held")
    private Positions positions;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();


    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public Staff() {
    }

    public Staff(UserType type, String first_name, String middle_name, String surname, String email,
                 String password, GenderEnum gender, String title, String phone_number, String marital_status,
                 String spouse_name, String spouse_phone, String avatar, Date employment_date, User user,
                 Organization organization, Positions positions, String created_by) {
        this.type = type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.title = title;
        this.phone_number = phone_number;
        this.marital_status = marital_status;
        this.spouse_name = spouse_name;
        this.spouse_phone = spouse_phone;
        this.avatar = avatar;
        this.employment_date = employment_date;
        this.created_by = created_by;
        this.user = user;
        this.positions = positions;
        this.organization = organization;
    }

    public Staff(UUID staff_id, UserType type, String first_name, String middle_name, String surname, String email,
                 String password, GenderEnum gender, String title, String phone_number, String marital_status,
                 String spouse_name, String spouse_phone, String avatar, Date employment_date, String created_by,
                 User user, Organization organization, Positions positions) {
        this.staff_id = staff_id;
        this.type = type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.title = title;
        this.phone_number = phone_number;
        this.marital_status = marital_status;
        this.spouse_name = spouse_name;
        this.spouse_phone = spouse_phone;
        this.avatar = avatar;
        this.employment_date = employment_date;
        this.created_by = created_by;
        this.user = user;
        this.organization = organization;
        this.positions = positions;
    }

    public Staff(UserType teachingStaff, String org_name, String org_name1, String org_name2,
                 String emailAddress, String s, User user1, Organization school, Positions positions, String created_by) {
        this.type = teachingStaff;
        this.first_name = org_name;
        this.middle_name = org_name2;
        this.surname = org_name1;
        this.email = emailAddress;
        this.phone_number = s;
        this.user = user1;
        this.organization = school;
        this.positions = positions;
        this.created_by = created_by;

    }
}
