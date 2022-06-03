package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {AddressMapper.class,CityMapper.class})
public interface ShopMapper {

    @Mapping(target = "addressDetails", source = "shop.address")
    @Mapping(target = "location", source = "shop.address.city")
    @Mapping(target = "userId", source = "shop.user.id")
    ShopDto toDto (Shop shop);

    @Mapping(target = "address", source = "shopDto.addressDetails")
    @Mapping(target = "user.id", source = "shopDto.userId")
    Shop toModel(ShopDto shopDto);

    List<ShopDto> toDtoList(List<Shop> shopList);

    List<Shop> toModelList(List<ShopDto> shopDtoList);
}
