package lunna.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * IntelliJ IDEA
 * school
 * ExpectationFailedException
 *
 * @author Collins K. Sang
 * 2021/07/22 11:16
 **/
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ExpectationFailedException extends RuntimeException{
    public ExpectationFailedException(String message){
        super(message);
    }
}
