package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.Otp;
import lunna.school.exception.BadRequestException;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.User;
import lunna.school.security.jwt.JwtUtils;
import lunna.school.service.OtpService;
import lunna.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * IntelliJ IDEA
 * school
 * OtpController
 *
 * @author Collins K. Sang
 * 2021/07/13 16:03
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OtpController {
    private final Logger log = Logger.getLogger(getClass().getName());

    final public OtpService otpService;

    final UserService userService;

    final AuthenticationManager authenticationManager;

    final JwtUtils jwtUtils;

    @Autowired
    public OtpController(OtpService otpService, UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.otpService = otpService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Create account using phone number
     *
     * @param email user phone number
     * @return ResponseBodyEntity
     */

    @GetMapping("/auth/{email}/getOtp")
    @ResponseBody
    public ApiResponse generateOtp(@PathVariable String email) {

        if (email == null) {
            throw new BadRequestException("Email is null");
        }

        String otp = otpService.generateOTP(email);
        User userData = userService.findUserByEmail(email);
        if (userData == null) {
            log.log(Level.SEVERE, "Email not found");
            throw new RecordNotFoundException("User not found.");
        }

        Otp obj = new Otp();
        obj.setEmail(email);
        obj.setOtp(otp);
        return new ApiResponse(
                obj,
                "Verify the Otp Provided",
                HttpStatus.OK.value());

    }

    /**
     * Validate Email using OTP
     *
     * @param otp one time passphrase or password
     * @return validation object and status
     */

    @PostMapping(value = "/auth/validateOtp")
    public ApiResponse validateOtp(@RequestBody Otp otp) {
        //Validate the Otp
        if (otp.getEmail() == null | otp.getEmail().isEmpty()) {
            log.log(Level.SEVERE, "Email field required");
            throw new BadRequestException("email field required");
        }
        if (otp.getOtp() == null | otp.getOtp().isEmpty()) {
            log.log(Level.SEVERE, "OTP field required");
            throw new BadRequestException("OTP field required");
        }

        String otp_num = otp.getOtp();
        if (otp_num != null) {
            /*
             * get opt from cache
             */
            String serverOtp = otpService.getOtp(otp.getEmail());
            log.log(Level.SEVERE, "otp " + serverOtp);

            if (serverOtp != null) {
                /*
                 * verify if OTP given is the same as the one in server
                 */

                if (otp_num.equals(serverOtp)) {
                    otp.setIsVerified(true);

                    User user = userService.findUserByEmail(otp.getEmail());
//                    if (user != null)
                    if (user.isActive()) {
                        /*
                         * remove OTP from cache
                         */

                        otpService.clearOTP(otp.getEmail());
                        /*
                         * Success response body
                         */
                        return new ApiResponse(
                                null,
                                "OTP successfully validated",
                                HttpStatus.OK.value()
                        );
                    }
                    /*
                     * remove OTP from cache
                     */

                    otpService.clearOTP(otp.getEmail());

                    /*
                     * Success response body
                     */
                    return new ApiResponse(
                            otp.getEmail(),
                            otp.getIsVerified().toString(),
                            HttpStatus.OK.value());
                } else {
                    otp.setIsVerified(false);
                    throw new BadRequestException(String.format("Invalid or expired OTP %s", otp.getOtp()));
                }
            } else {
                otp.setIsVerified(false);
                throw new RecordNotFoundException(String.format("User not registered %s", otp.getEmail()));
            }
        } else {
            otp.setIsVerified(false);
            throw new BadRequestException(String.format("Invalid or expired OTP %s", otp.getOtp()));
        }
    }

}
