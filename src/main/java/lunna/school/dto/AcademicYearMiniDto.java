package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * Collins K. Sang
 * 6/13/22 11:10 AM
 * school
 * AcademicYearMiniDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class AcademicYearMiniDto {
    private UUID academic_year_id;
    private Date date_from;
    private Date date_to;
    private String academic_year_name;
    private boolean status;
    private Date createdAt;

}
