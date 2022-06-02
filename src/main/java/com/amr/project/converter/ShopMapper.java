package com.amr.project.converter;


import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Coupon;

import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ReviewMapper.class, CityMapper.class, DiscountMapper.class, ImageMapper.class},
        imports = {Coupon.class, Collectors.class})
public interface ShopMapper {
    @Mapping(target = "couponIds",
            expression = "java(shop.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList()))")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "location", source = "address.city")
    ShopDto toDto(Shop shop);

    Shop toModel(ShopDto shopDto);

    List<ShopDto> toDtoList(List<Shop> shopList);

    List<Shop> toModelList(List<ShopDto> shopDtoList);

}
