package com.invoice.request.processor.function.exception;


import lombok.Data;

import java.util.List;

@Data
public class RequestProcessorError {

    private String errorDetail;
    private List<String> errorMessages;
}
