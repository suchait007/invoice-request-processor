package org.scf.azure.gradle.exception;


import lombok.Data;

import java.util.List;

@Data
public class RequestProcessorError {

    private String errorDetail;
    private List<String> errorMessages;
}
