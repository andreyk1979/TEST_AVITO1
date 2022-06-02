package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ReviewMapper.class, DiscountMapper.class, UserMapper.class},
        imports = {Coupon.class, Collectors.class})
public interface ShopMapper {
    @Mapping(target = "couponIds",
            expression = "java(shop.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList()))")
    ShopDto toDto(Shop shop);

    Shop toModel(ShopDto shopDto);

    List<ShopDto> toDtoList(List<Shop> shopList);

    List<Shop> toModelList(List<ShopDto> shopDtoList);

}
