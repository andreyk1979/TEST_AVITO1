package com.amr.project.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Bill {
    @Id
    @Column(name = "bill_Id", nullable = false, unique = true, length = 250)
    private String billId;

    @Column(name = "amount_value")
    private BigDecimal amountValue;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "expiration_date_time")
    private ZonedDateTime expirationDateTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private String status;
}
