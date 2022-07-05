package lunna.school.exception;

import lombok.extern.slf4j.Slf4j;
import lunna.school.dto.ApiResponse;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 8:05 AM
 **/
@ControllerAdvice
@Slf4j
public class ApiErrorAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiResponse> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public final ResponseEntity<ApiResponse> handleUserNotFoundException(AccessDeniedException ex) {
        return error(UNAUTHORIZED, "Unauthorized");
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ApiResponse> handleConflict(RuntimeException ex) {
        return error(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ApiResponse> handleLockedException(LockedException e) {
        return error(LOCKED, e);
    }

    @ExceptionHandler({NoContentException.class})
    public ResponseEntity<ApiResponse> handleNoContentException(NoContentException e) {
        return error(NO_CONTENT, e);
    }

    @ExceptionHandler({ExpectationFailedException.class})
    public ResponseEntity<ApiResponse> handleNoContentException(ExpectationFailedException e) {
        return error(EXPECTATION_FAILED, e);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFoundException(RecordNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ApiResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return error(CONFLICT, e);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e) {
        return error(BAD_REQUEST, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Throwable cause = e.getRootCause();
        Exception msg = null;
        Set<String> messages = null;
        String message = e.getMostSpecificCause().getMessage();
        logger.info("\n\n========>");
        logger.error(message);

        if (message != null) {
            message = message.split("for")[0];
        } else {
            message = e.getLocalizedMessage();
        }

        if (cause instanceof SQLIntegrityConstraintViolationException) {

            SQLIntegrityConstraintViolationException consEx = (SQLIntegrityConstraintViolationException) cause;

            messages.add(consEx.getLocalizedMessage());

        }

//        if (cause instanceof ConstraintViolationException) {
//            ConstraintViolationException ex = (ConstraintViolationException) cause;
//            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
//            messages = new HashSet<>(constraintViolations.size());
//            messages.addAll(constraintViolations.stream()
//                    .map(constraintViolation -> String.format("Error value '%s' %s",
//                            constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
//                    .collect(Collectors.toList()));
//        }

        if (cause instanceof DataIntegrityViolationException) {
            messages.add(message);
        }

        return error(BAD_REQUEST, messages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("Error value '%s' %s",
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList()));
        return error(BAD_REQUEST, messages);

    }

    @Override
//    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request ";
        ApiResponse response = new ApiResponse(
                status,
                error + ex.getLocalizedMessage(),
                status.value()

        );
        return new ResponseEntity<>(
                response,
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println(errors);
        ApiResponse error = new ApiResponse(errors, "Validation failed", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ApiResponse> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return new ResponseEntity<>(
                new ApiResponse(null, e.getMessage(), status.value()),
                status);
    }

    private ResponseEntity<ApiResponse> error(HttpStatus status, Set<String> e) {
        log.error("Exception : ", e);
        return new ResponseEntity<>(
                new ApiResponse(null, e.toString(), status.value()),
                status);
    }

    private ResponseEntity<Object> error(HttpStatus status, List<FieldError> e) {
        log.error("Exception : ", e);
        return new ResponseEntity<>(
                new ApiResponse(null, e.toString(), status.value()),
                status);
    }

    private ResponseEntity<ApiResponse> error(HttpStatus status, String msg) {
        log.error("Exception : ", msg);
        return new ResponseEntity<>(
                new ApiResponse(null, msg, status.value()),
                status);
    }
}
