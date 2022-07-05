package lunna.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 21. Jul 2021 3:33 PM
 **/
@ResponseStatus(HttpStatus.LOCKED)
public class LockedException extends RuntimeException{
    public LockedException(String message){
        super(message);
    }
}
