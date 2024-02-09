package com.invoice.request.processor.function.handler;


import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.function.Function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.adapter.azure.AzureFunctionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

@Component
public class TestHandler {

    @Autowired
    private Function<Message<String>, String> uppercase;

    @FunctionName("bean")
    public String plainBean(
            @HttpTrigger(name = "req", methods = { HttpMethod.GET,
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        // Inject the ExecutionContext as Message header
        Message<String> enhancedRequest = (Message<String>) AzureFunctionUtil.enhanceInputIfNecessary(
                request.getBody().get(),
                context);

        return this.uppercase.apply(enhancedRequest);
    }

    @Bean
    public Function<Message<String>, String> uppercase() {
        return message -> {
            ExecutionContext context = (ExecutionContext) message.getHeaders().get(AzureFunctionUtil.EXECUTION_CONTEXT);

            String updatedPayload = message.getPayload().toUpperCase();

            context.getLogger().info("Azure Test: " + updatedPayload);

            return message.getPayload().toUpperCase();
        };
    }
}
