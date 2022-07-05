package lunna.school.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author : HAron Korir
 * @mailto : koryrh@mail.com
 * @created : 4/22/22, Friday
 **/
@Getter
@Setter
public class ErrorResponse {
    //General error message about nature of error
    private String message;
    private HttpStatus value;
    private int status;

    //Specific errors in API request processing
    private Object details;
    public ErrorResponse(){}

    public ErrorResponse(int status, HttpStatus value, String message, Object details) {
        super();
        this.message = message;
        this.details = details;
        this.value = value;
        this.status = status;

    }



}

