package lunna.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * IntelliJ IDEA
 * school
 * NoContentException
 *
 * @author Collins K. Sang
 * 2021/07/22 11:09
 **/
@ResponseStatus(HttpStatus.NO_CONTENT)

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
