package lunna.school.controller;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/13/22, Friday
 **/

import org.springframework.beans.factory.annotation.Value;
import lunna.school.model.Mail;
import lunna.school.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    public EmailService emailService;
    @Value("${attachment.invoice}")
    private String attachmentPath;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);

        //Email with attachment
        props = new HashMap<>();
        props.put("headerText", "Send Email With Attachment");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "To make sure that you send an attachment with this email, change the value for the 'attachment.invoice' in the application.properties file to the path to the attachment.");
        labels.put("sendAttachment", props);
    }

    @PostMapping("/send")
    public String createMail(@RequestBody @Valid Mail mailObject,
                             BindingResult errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        return "emails";
    }

    @PostMapping("/sendAttachment")
    public String createMailWithAttachment(
                                           @RequestBody @Valid Mail mailObject,
                                           BindingResult errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendMessageWithAttachment(
                mailObject.getTo(),
                mailObject.getSubject(),
                mailObject.getText(),
                attachmentPath
        );

        return "redirect:/mail";
    }
}
