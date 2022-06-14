package com.amr.project.converter;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AddressMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toModel(OrderDto orderDto);
}
