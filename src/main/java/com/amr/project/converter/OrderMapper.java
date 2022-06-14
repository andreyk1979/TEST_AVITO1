package com.amr.project.converter;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.*;
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
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AddressMapper.class, ImageMapper.class, ItemMapper.class},
        imports = {LocalDate.class, Calendar.class, ZoneId.class, Collectors.class, User.class })
public interface OrderMapper {

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "date", source = "order.orderDate")
    @Mapping(target = "total", source = "order.grandTotal")
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    Order toModel(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orders);

}
