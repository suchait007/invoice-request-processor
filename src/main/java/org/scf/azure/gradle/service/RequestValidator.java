package org.scf.azure.gradle.service;


import org.scf.azure.gradle.dto.Invoice;
import org.scf.azure.gradle.exception.ProcessorException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class RequestValidator {

    public void validate(Invoice invoice) {

        List<String> errorMessages = new ArrayList<>();

        if(isBlank(invoice.getInvoiceNumber())) {
            errorMessages.add("Invoice cannot be empty.");
        }

        if(isBlank(invoice.getCustomerId())) {
            errorMessages.add("Customer Id cannot be empty");
        }

        if( invoice.getLineItems() == null || invoice.getLineItems().size() == 0) {
            errorMessages.add("No line items present in invoice.");
        }

        if( errorMessages.size() > 0) {
            throw new ProcessorException("Invalid request passed", errorMessages, HttpStatus.BAD_REQUEST.value());
        }
    }
}
