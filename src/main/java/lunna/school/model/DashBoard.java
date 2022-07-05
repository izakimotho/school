package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/15/22, Sunday
 **/
@Getter
@Setter
public class DashBoard {
    private Long users;
    private Long staffs;
    private Long subjects;
    private Long students;
    private Long classes;
    public DashBoard(){}
}
