package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:05 AM
 **/
@Getter
@Setter
@Entity
@Table(name = "county")
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "county_id", updatable = false, nullable = false)
    private Long county_id;
    private String county_name;
    private String county_code;

    public County(){}

}
