package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 23. Jul 2021 5:00 PM
 **/
@Getter
@Setter
public class SubjectDto {

    private UUID subject_id;
    private String subject_name;
    private String subject_abbr;
    public SubjectDto(){}
}
