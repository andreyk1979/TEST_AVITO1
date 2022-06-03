package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ImageMapper.class, ReviewMapper.class})
public interface ItemMapper {
    @Mapping(target = "id", source = "item.id")
    ItemDto toDto(Item item);

    Item toModel(ItemDto itemDto);

    List<ItemDto> toDtoList(List<Item> itemList);

    List<Item> toModelList(List<ItemDto> itemDtoList);
}

