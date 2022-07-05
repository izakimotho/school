package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 3:33 PM
 **/
@Getter
@Setter
@Entity
@Table(name = "user_level")
public class UserLevel {
    @Id
    @Column(unique = true)
    private Integer user_level;

    @Column(unique = true)
    private String category_name;


    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    public UserLevel(){

    }
}
