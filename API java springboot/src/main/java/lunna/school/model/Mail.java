package lunna.school.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/13/22, Friday
 **/
@Getter
@Setter
public class Mail {
    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the message to it")
    private String to;
    private String recipientName;
    private String subject;
    private String text;
    private String senderName;
    public Mail(){}
}
