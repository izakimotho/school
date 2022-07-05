package lunna.school.utils;

import org.apache.commons.text.RandomStringGenerator;

import java.security.SecureRandom;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/13/22, Friday
 **/
public class Util {
    public static String generatePassword(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
