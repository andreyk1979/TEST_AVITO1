package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    @Mapping(target = "id", source = "shop.id")
    @Mapping(target = "name", source = "shop.name")
    @Mapping(target = "userId", source = "shop.user.id")
    @Mapping(target = "location", source = "cityDto")
    @Mapping(target = "couponIds", source = "coupons")
    ShopDto toDto(Shop shop, CityDto cityDto, List<Long> coupons);


}
