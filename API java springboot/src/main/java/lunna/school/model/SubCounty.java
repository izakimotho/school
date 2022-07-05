package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * IntelliJ IDEA
 * school
 * SubCounty
 *
 * @author Collins K. Sang
 * 2021/06/30 15:53
 **/
@Entity
@Table(name = "sub_county")
@Getter
@Setter
@ToString
public class SubCounty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sub_county_id;
    private Long county_code;
    private String county_name;
    private String sub_county_name;

    public SubCounty() {
    }

    public SubCounty(long sub_county_id, Long county_code, String county_name, String sub_county_name) {
        this.sub_county_id = sub_county_id;
        this.county_code = county_code;
        this.county_name = county_name;
        this.sub_county_name = sub_county_name;
    }
}
