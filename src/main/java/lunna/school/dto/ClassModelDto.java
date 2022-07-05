package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author : HAron Korir
 * @mailto : koryrh@mail.com
 * @created : 4/25/22, Monday
 **/
@Getter
@Setter
public class ClassModelDto {
    private UUID class_id;
    private String class_name;
    private String description;

    public ClassModelDto(){}
}
