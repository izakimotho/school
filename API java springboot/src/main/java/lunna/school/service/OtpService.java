package lunna.school.service;


/**
 * IntelliJ IDEA
 * school
 * OtpService
 *
 * @author Collins K. Sang
 * 2021/07/13 16:03
 **/

public interface OtpService {
    String generateOTP(String phone_number);

    String getOtp(String phone_number);

    void clearOTP(String phone_number);
}
