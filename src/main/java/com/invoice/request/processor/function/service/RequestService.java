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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final AccessLogRepo accessLogRepo;
    private final Gson gson = new Gson();

    private final JmsTemplate jmsTemplate;

    @Transactional
    public void logAndSubmitInQueue(Invoice invoice) {

        if(isNull(invoice)) {
            throw new ProcessorException("Invoice object is empty", List.of("No value present in invoice"), HttpStatus.BAD_REQUEST.value());
        }

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
