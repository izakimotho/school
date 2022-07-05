package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 26. Jun 2021 7:29 PM
 **/
@Getter
@Setter
public class ApiResponse implements Serializable {
    private int status;
    private String message;
    private Object result;
    public ApiResponse(){}
    public ApiResponse(Object result,String message,int status){
        this.result = result;
        this.message = message;
        this.status = status;
    }


}
