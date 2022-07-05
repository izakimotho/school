package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 3:20 PM
 **/

@Setter
@Getter
public class Parent {
    private UUID student_id;
    private  String parent_name;
    private Long phone_number;
    public Parent(){}

    public Parent(UUID student_id, String parent_name, Long phone_number) {
        this.student_id = student_id;
        this.parent_name = parent_name;
        this.phone_number = phone_number;
    }
}
