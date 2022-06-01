package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiscountMapper {

    @Mapping(target = "userId", source = "shop.user.id")
    @Mapping(target = "shopId", source = "shop.id")
    DiscountDto toDto(Discount discount);
}
