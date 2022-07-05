package lunna.school.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lunna.school.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * IntelliJ IDEA
 * school
 * OtpServiceImpl
 *
 * @author Collins K. Sang
 * 2021/07/13 16:03
 **/
@Service("otpService")
public class OtpServiceImpl implements OtpService {
    private static final Integer EXPIRE_MINS = 5;
    private final LoadingCache<String, String> otpCache;

    public OtpServiceImpl() {
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
            public String load(String key) {
                return "";
            }
        });
    }
    /**
     * Function to generate OTP
     *
     */
    @Override
    public String generateOTP(String email) {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));
        otpCache.put(email, otp);
        return otp;
    }

    @Override
    public String getOtp(String phone_number) {
        try {
            return otpCache.get(phone_number);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void clearOTP(String phone_number) {
        otpCache.invalidate(phone_number);
    }
}
