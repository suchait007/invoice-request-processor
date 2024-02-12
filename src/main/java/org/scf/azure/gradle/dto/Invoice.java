package org.scf.azure.gradle.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Invoice {

    private String invoiceNumber;
    private String customerName;
    private String customerId;
    private List<LineItem> lineItems;
}
