package com.amr.project.converter;

import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ShopMapper.class, UserMapper.class})
public interface CouponMapper {

    @Mapping(target = "userId", source = "user.id")
    CouponDto toDto(Coupon coupon);

    Coupon toModel(CouponDto couponDto);
    List<CouponDto> tolist(List<Coupon> couponList);
}
