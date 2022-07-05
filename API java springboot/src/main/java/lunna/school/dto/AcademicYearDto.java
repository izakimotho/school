package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Organization;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/14/22, Saturday
 **/
@Getter
@Setter
public class AcademicYearDto {
    private UUID academic_year_id;
    private Date date_from;
    private Date date_to;
    private String academic_year_name;
    private boolean status;
    private SchoolDto organization;
    private Date createdAt;

    public AcademicYearDto(){}
}
