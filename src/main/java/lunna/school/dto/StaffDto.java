package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 23. Jul 2021 4:46 PM
 **/
@Getter
@Setter
public class StaffDto {
    private UUID staff_id;
    private UserType type;
    private String first_name;
    private String middle_name;
    private String surname;
    private String email;
    private String password;
    private GenderEnum gender;
    private String title;
    private String phone_number;
    private String marital_status;
    private String spouse_name;
    private String spouse_phone;
    private String photo_url;
    private Date employment_date;
    public StaffDto(){}
}
