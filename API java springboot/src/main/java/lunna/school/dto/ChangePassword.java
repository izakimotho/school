package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * IntelliJ IDEA
 * school
 * ChangePassword
 *
 * @author Collins K. Sang
 * 2021/07/19 11:06
 **/
@Getter
@Setter
@ToString
public class ChangePassword {
    private String old_password;
    private String new_password;
    private String confirm_password;
}
