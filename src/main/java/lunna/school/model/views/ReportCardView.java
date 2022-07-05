package lunna.school.model.views;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
@Getter
@Setter
@Entity
@Immutable
@Table(name = "views_report_card")
public class ReportCardView {
    @Id
    private String adm_number;
    private String first_name;
    private String surname;
    private float grade;
    private String org_name;
    private String subject_name;

    public ReportCardView() {
    }
}
