package com.invoice.request.processor.function.service;


import com.google.gson.Gson;
import com.invoice.request.processor.function.dto.Invoice;
import com.invoice.request.processor.function.entities.AccessLog;
import com.invoice.request.processor.function.exception.ProcessorException;
import com.invoice.request.processor.function.repos.AccessLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final AccessLogRepo accessLogRepo;
    private final Gson gson = new Gson();
    private final JmsTemplate jmsTemplate;
    private final RequestValidator requestValidator;

    public void logAndSubmitInQueue(Invoice invoice) {

        requestValidator.validate(invoice);

        log.info("Request received : {} ", invoice);

        try {
            accessLogRepo.save(populateAccessLog(invoice));
        } catch (DataAccessException dataAccessException) {
            log.error("Database exception occurred : {} ", dataAccessException.getMessage());
            throw new ProcessorException("Database access exception occurred",
                    List.of(dataAccessException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        log.info("Message has been saved in database.");

        try {
            jmsTemplate.convertAndSend("new-invoices", invoice);
        } catch (JmsException jmsException) {
            log.error("Database exception occurred : {} ", jmsException.getMessage());
            throw new ProcessorException("Jms exception occurred",
                    List.of(jmsException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        log.info("Message has been submitted to inter service connector queue.");

    }

    private AccessLog populateAccessLog(Invoice invoice) {

        AccessLog accessLog = new AccessLog();

        accessLog.setInvoiceNumber(invoice.getInvoiceNumber());
        accessLog.setCustomerName(invoice.getCustomerName());
        accessLog.setRequest(gson.toJson(invoice));

        return accessLog;
    }
}
