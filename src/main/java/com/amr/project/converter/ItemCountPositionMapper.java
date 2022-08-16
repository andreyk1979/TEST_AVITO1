package com.amr.project.converter;

import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.entity.ItemCountPosition;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {ItemMapper.class})
public interface ItemCountPositionMapper {

    ItemCountPositionDto toDto(ItemCountPosition itemCountPosition);
    ItemCountPosition toModel(ItemCountPositionDto itemCountPositionDto);
}

