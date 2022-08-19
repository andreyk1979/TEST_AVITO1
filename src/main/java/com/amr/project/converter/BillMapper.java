package com.amr.project.converter;

import com.amr.project.model.dto.BillDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BillMapper {

    @Mapping(target = "billId", source = "bill.billId")
    @Mapping(target = "amountValue", source = "bill.amountValue")
    @Mapping(target = "customerEmail", source = "bill.customerEmail")
    @Mapping(target = "customerPhone", source = "bill.customerPhone")
    @Mapping(target = "expirationDateTime", source = "bill.expirationDateTime")
    @Mapping(target = "comment", source = "bill.comment")

    BillDto toDto(Bill bill);

    Bill toModel(BillDto billDto);

}
