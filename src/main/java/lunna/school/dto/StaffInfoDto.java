package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * StaffInfoDto
 *
 * @author Collins K. Sang
 * 2021/06/30 09:17
 **/

@Getter
@Setter
@ToString
public class StaffInfoDto {

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
    private SchoolDto organization;
    private UserLevel user_category;
    private PositionDto positions;
    private Set<Role> role;

    public StaffInfoDto() {
    }

    public StaffInfoDto(UserType type, String first_name, String middle_name, String surname, String email,
                        String password, GenderEnum gender, String title, String phone_number, String marital_status,
                        String spouse_name, String spouse_phone, String photo_url, Date employment_date, SchoolDto organization) {
        this.type = type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.title = title;
        this.phone_number = phone_number;
        this.marital_status = marital_status;
        this.spouse_name = spouse_name;
        this.spouse_phone = spouse_phone;
        this.photo_url = photo_url;
        this.employment_date = employment_date;
        this.organization = organization;
    }

    public StaffInfoDto(UserType type, String first_name, String middle_name, String surname, String email,
                        GenderEnum gender, String title, String phone_number, String marital_status, String spouse_name,
                        String spouse_phone, String photo_url, Date employment_date, SchoolDto organization) {
        this.type = type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.title = title;
        this.phone_number = phone_number;
        this.marital_status = marital_status;
        this.spouse_name = spouse_name;
        this.spouse_phone = spouse_phone;
        this.photo_url = photo_url;
        this.employment_date = employment_date;
        this.organization = organization;
    }

    public StaffInfoDto(UUID staff_id, UserType type, String first_name, String middle_name, String surname,
                        String email, GenderEnum gender, String title, String phone_number, String marital_status,
                        String spouse_name, String spouse_phone, String photo_url, Date employment_date, SchoolDto organization, PositionDto positions) {
        this.staff_id = staff_id;
        this.type = type;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.title = title;
        this.phone_number = phone_number;
        this.marital_status = marital_status;
        this.spouse_name = spouse_name;
        this.spouse_phone = spouse_phone;
        this.photo_url = photo_url;
        this.employment_date = employment_date;
        this.organization = organization;
        this.positions = positions;
    }
}
