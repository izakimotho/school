package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 28. Jul 2021 11:54 AM
 **/
@Getter
@Setter
public class ExamsDto {
    private String exam;
    private float marks;
    public ExamsDto(){}

    public ExamsDto(String exam, float marks){
        this.exam = exam;
        this.marks = marks;
    }
}
