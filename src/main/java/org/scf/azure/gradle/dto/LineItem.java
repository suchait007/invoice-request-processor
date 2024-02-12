package org.scf.azure.gradle.dto;


import lombok.Data;

@Data
public class LineItem {

    private String itemName;
    private String itemId;
    private Double itemPrice;
    private Integer quantity;
    private Double discount;
}
