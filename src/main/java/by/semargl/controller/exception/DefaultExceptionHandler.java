package by.semargl.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Throwable e) {
        /* Handles all other exceptions. Status code 500. */

        return new ResponseEntity<>(new ErrorMessage(1L, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
