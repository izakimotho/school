package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Terms;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/19/22 9:37 AM
 * school
 * TermDetailsDtos
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class TermDetailsDtos {
    private String term_details_id;
    private TermsDto terms;
    private Date date_from;
    private Date date_to;
    private String term_description;
    private boolean status;
    private AcademicYearDto academic_year;
}
