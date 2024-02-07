package com.invoice.request.processor.function.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class Invoice {

    private String invoiceNumber;
    private String customerName;
    private String customerId;
    private LocalDateTime date;
    private List<LineItem> lineItems;
}
