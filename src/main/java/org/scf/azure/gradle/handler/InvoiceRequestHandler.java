package org.scf.azure.gradle.handler;



import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import lombok.RequiredArgsConstructor;
import org.scf.azure.gradle.dto.Invoice;
import org.scf.azure.gradle.dto.RequestProcessorResponse;
import org.scf.azure.gradle.service.RequestService;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class InvoiceRequestHandler {

    private final RequestService requestService;

    @FunctionName("invoiceProcessor")
    public HttpResponseMessage execute(@HttpTrigger(name = "request", methods = {HttpMethod.POST},
            authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<Invoice>> request,
                                       ExecutionContext context) {

        processRequest(request);

        return request
                .createResponseBuilder(HttpStatus.OK)
                .body(new RequestProcessorResponse("Accepted"))
                .header("Content-Type", "application/json")
                .build();

    }

    private void processRequest(HttpRequestMessage<Optional<Invoice>> request) {
        request.getBody()
                .ifPresent(requestBody -> requestService.logAndSubmitInQueue(requestBody));
    }
}


