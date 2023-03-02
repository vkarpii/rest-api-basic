package com.epam.esm.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class APIExceptionHandler {
    private final MessageSource messageSource;

    public APIExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity handleException(ApplicationException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getMessage(), new Object[]{}, locale);
        APIException apiException = new APIException(message, exception.getMessage());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);//повертати код як в ексепшині
    }
}
