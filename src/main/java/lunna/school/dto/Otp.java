package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


/**
 * IntelliJ IDEA
 * school
 * OTP
 *
 * @author Collins K. Sang
 * 2021/07/13 16:03
 **/
@Getter
@Setter
@ToString
public class Otp {
    @NotBlank
    private String email;
    private String otp;
    private Boolean isVerified = false;

    public Otp() {
    }
}
