package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
uses = {ShopMapper.class, UserMapper.class})
public interface DiscountMapper {

    @Mapping(target = "userId", source = "shop.user.id")
    @Mapping(target = "shopId", source = "shop.id")
    DiscountDto toDto(Discount discount);

    //TODO возврат моделей нужно реализовать @Mapping если нужен
    Discount toModel(DiscountDto discountDto);
    List<DiscountDto> tolist(List<Discount> discountList);
}
