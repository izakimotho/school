package lunna.school.service;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/13/22, Friday
 **/
public interface EmailService {
    void sendMessage(String to,
                           String subject,
                           String text);
    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
}
