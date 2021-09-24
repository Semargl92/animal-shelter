package by.semargl.controller.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.semargl.exception.NoSuchEntityException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandlers {

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ErrorMessage> handleNoSuchEntityException(NoSuchEntityException e) {
        return new ResponseEntity<>(new ErrorMessage(2L, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(JsonMappingException e) {
        return new ResponseEntity<>(new ErrorMessage(3L, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ErrorMessage(4L, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(AuthenticationException e) {
        return new ResponseEntity<>(new ErrorMessage(5L, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
