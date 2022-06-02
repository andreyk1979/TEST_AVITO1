package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ImageMapper.class, ReviewMapper.class})
public interface ItemMapper {

    ItemDto toDto(Item item);

    //TODO возврат моделей нужно реализовать @Mapping если нужен
    Item toModel(ItemDto itemDto);

    List<ItemDto> toDtoList(List<Item> itemList);

    List<Item> toModelList(List<ItemDto> itemDtoList);
}
