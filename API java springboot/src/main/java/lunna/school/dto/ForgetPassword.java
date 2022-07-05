package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * IntelliJ IDEA
 * school
 * ForgetPassword
 *
 * @author Collins K. Sang
 * 2021/07/21 10:12
 **/
@Getter
@Setter
@ToString
public class ForgetPassword {
    @NotBlank
    @Email
    @NotEmpty(message = "*Please provide your email")
    private String email;
    @NotBlank
    @NotEmpty(message = "*Please provide new password")
    private String new_password;
    @NotBlank
    @NotEmpty(message = "*Please confirm your password")
    private String confirm_password;
    public ForgetPassword(){}
}
