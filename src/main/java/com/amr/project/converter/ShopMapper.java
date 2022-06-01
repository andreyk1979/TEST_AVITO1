package com.amr.project.converter;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShopMapper {

    @Mapping(target = "couponIds", source = "coupons")
    ShopDto toDto(Shop shop, List<Long> coupons);

    default List<Long> getCouponList(Shop shop) {
       return shop.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList());
    }


}
