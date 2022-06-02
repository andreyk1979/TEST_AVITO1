package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, ShopMapper.class})
public interface DiscountMapper {

    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "userId", source = "shop.user.id")
    DiscountDto toDto(Discount discount);

    Discount toModel(DiscountDto discountDto);
}
