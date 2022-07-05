package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Subject;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 23. Jul 2021 3:46 PM
 **/
@Getter
@Setter
public class StreamDetailsDto {
    private UUID stream_detail_id;

    private SubjectDto subject;

    private StaffDto staff;

    public StreamDetailsDto(){}
}
