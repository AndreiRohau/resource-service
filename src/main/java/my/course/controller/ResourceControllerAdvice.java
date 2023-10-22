package my.course.controller;

import my.course.exception.ResponseServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ResponseServiceException.class })
    public ResponseEntity<Object> handleResponseServiceException(ResponseServiceException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }
}