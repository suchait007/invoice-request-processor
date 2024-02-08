package com.invoice.request.processor.function.controller;

import com.invoice.request.processor.function.dto.Invoice;
import com.invoice.request.processor.function.dto.RequestProcessorResponse;
import com.invoice.request.processor.function.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TestController {

    private final RequestService requestService;

    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RequestProcessorResponse testFunctionApi(@RequestBody Invoice invoice) {
        requestService.logAndSubmitInQueue(invoice);
        return new RequestProcessorResponse("Accepted");
    }
}
