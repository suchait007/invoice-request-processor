package com.invoice.request.processor.function.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class AccessLog {

    @Column(name = "access_log_id")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "request")
    private String request;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createTimestamp;

}
