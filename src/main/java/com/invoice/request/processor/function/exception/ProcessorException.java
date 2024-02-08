package com.invoice.request.processor.function.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProcessorException extends RuntimeException{

    private String errorDetail;
    private List<String> errorMessages;
    private Integer statusCode;

    public ProcessorException(String errorDetail, List<String> errorMessages, Integer statusCode) {
        super(errorDetail);
        this.errorDetail = errorDetail;
        this.errorMessages = new ArrayList<>(errorMessages);
        this.statusCode = statusCode;

    }
}
