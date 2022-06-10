package com.amr.project.converter;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ReviewMapper.class, ImageMapper.class, DiscountMapper.class, CityMapper.class,AddressMapper.class},
        imports = {Coupon.class, Collectors.class})
public interface ShopMapper {

    @Mapping(target = "addressDetails", source = "shop.address")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "location", source = "address.city")
    @Mapping(target = "couponIds", expression = "java(shop.getCoupons() != null ? shop.getCoupons().stream()" +
            ".map(Coupon::getId).collect(Collectors.toList()) : null)")
    ShopDto toDto(Shop shop);

    @Mapping(target = "address", source = "shopDto.addressDetails")
    Shop toModel(ShopDto shopDto);

    List<ShopDto> toDtoList(List<Shop> shopList);

    List<Shop> toModelList(List<ShopDto> shopDtoList);
}
