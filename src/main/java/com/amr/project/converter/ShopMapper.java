package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShopMapper {
    ShopDto toDto(Shop shop);

    Shop toModel(ShopDto shopDto);

    List<ShopDto> toDtoList(List<Shop> shopList);

    List<Shop> toModelList(List<ShopDto> shopDtoList);
}
