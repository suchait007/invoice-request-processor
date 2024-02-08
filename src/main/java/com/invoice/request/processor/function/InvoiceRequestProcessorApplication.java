package com.invoice.request.processor.function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@EnableJms
@SpringBootApplication
public class InvoiceRequestProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceRequestProcessorApplication.class, args);
	}

}
