package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ReviewMapper.class, ImageMapper.class})
public interface ItemMapper {
    ItemDto toDto(Item item);

    Item toModel(ItemDto itemDto);

    List<ItemDto> toDtoList(List<Item> itemList);

    List<Item> toModelList(List<ItemDto> itemDtoList);
}
