package com.invoice.request.processor.function.service;


import com.google.gson.Gson;
import com.invoice.request.processor.function.dto.Invoice;
import com.invoice.request.processor.function.entities.AccessLog;
import com.invoice.request.processor.function.repos.AccessLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final AccessLogRepo accessLogRepo;
    private final Gson gson = new Gson();

    public void logAndSubmitInQueue(Invoice invoice) {

        log.info("Request received : {} ", invoice.toString());
        accessLogRepo.save(populateAccessLog(invoice));

    }

    private AccessLog populateAccessLog(Invoice invoice) {

        AccessLog accessLog = new AccessLog();

        accessLog.setInvoiceNumber(invoice.getInvoiceNumber());
        accessLog.setCustomerName(invoice.getCustomerName());
        accessLog.setRequest(gson.toJson(invoice));

        return accessLog;
    }
}
