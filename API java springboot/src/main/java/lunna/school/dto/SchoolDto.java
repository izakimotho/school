package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:41 AM
 **/
@Getter
@Setter
@ToString
public class SchoolDto {
    private UUID org_id;

    @NotBlank
    @NotEmpty(message = "*Please provide school name")
    private String org_name;
    private String code;
    private String motto;
    private List<String> phone_numbers;
    private List<String> mobile_phone_numbers;
    private String school_history;
    private String email_address;
    private String postal_address;
    private String logo;


    public SchoolDto() {
    }

    public SchoolDto(String org_name, String code) {
        this.org_name = org_name;
        this.code = code;
    }

    public SchoolDto(UUID org_id, String org_name, String code) {
        this.org_id = org_id;
        this.org_name = org_name;
        this.code = code;
    }

}
