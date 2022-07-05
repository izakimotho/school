package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * IntelliJ IDEA
 * school
 * UserType
 *
 * @author Collins K. Sang
 * 2021/07/13 09:12
 **/
@Getter
@Setter
@Entity
public class UserType implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long user_type_id;
    private String user_type_name;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public UserType(){}
//    STUDENT("STUDENT"),
//    TEACHING_STAFF("TEACHING STAFF"),
//    SUPPORT_STAFF("SUPPORT STAFF");
//    public final String label;
//
//    UserType(String label) {
//        this.label = label;
//    }
}
