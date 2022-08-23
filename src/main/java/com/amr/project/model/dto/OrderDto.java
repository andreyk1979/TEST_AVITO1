package com.amr.project.model.dto;

import com.amr.project.util.validation.basket.OnAdd;
import com.amr.project.util.validation.order.EnoughToLock;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private BigDecimal total;

    @Valid
    private AddressDto address;

    private Long userId;
    private List<ItemDto> itemsInOrder;

    @EnoughToLock(groups = OnAdd.class)
    private Map<Long, Integer> positionCount;
}
