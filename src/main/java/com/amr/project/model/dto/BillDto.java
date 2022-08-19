package com.amr.project.model.dto;

import com.amr.project.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "billId", scope = String.class)
public class BillDto {
    private String billId;
    private BigDecimal amountValue;
    private String customerEmail;
    private String customerPhone;
    private ZonedDateTime expirationDateTime;
    private String comment;
}
