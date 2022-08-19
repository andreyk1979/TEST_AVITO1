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
    @Column(name = "billId", nullable = false, unique = true, length = 250)
    private String billId;

    @Column(name = "amountValue")
    private BigDecimal amountValue;

    @Column(name = "customerEmail")
    private String customerEmail;

    @Column(name = "customerPhone")
    private String customerPhone;

    @Column(name = "expirationDateTime")
    private ZonedDateTime expirationDateTime;

    @Column(name = "comment")
    private String comment;
}
