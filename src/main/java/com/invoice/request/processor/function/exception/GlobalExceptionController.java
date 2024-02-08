package com.invoice.request.processor.function.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ProcessorException.class)
    public ResponseEntity<Object> handleException(ProcessorException ex) {

        RequestProcessorError requestProcessorError = new RequestProcessorError();
        requestProcessorError.setErrorMessages(ex.getErrorMessages());
        requestProcessorError.setErrorDetail(ex.getErrorDetail());

        return new ResponseEntity<>(requestProcessorError, HttpStatus.valueOf(ex.getStatusCode()));
    }
}
