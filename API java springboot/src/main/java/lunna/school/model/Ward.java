package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * IntelliJ IDEA
 * school
 * Ward
 *
 * @author Collins K. Sang
 * 2021/06/30 15:56
 **/
@Entity
@Table(name = "wards")
@Getter
@Setter
@ToString
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ward_id;
    private Long sub_county_code;
    private Long ward_code;
    private String ward_name;

    public Ward() {
    }

    public Ward(long ward_id, Long sub_county_code, Long ward_code, String ward_name) {
        this.ward_id = ward_id;
        this.sub_county_code = sub_county_code;
        this.ward_code = ward_code;
        this.ward_name = ward_name;
    }
}
