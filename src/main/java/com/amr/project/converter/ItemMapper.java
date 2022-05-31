package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);
    @Mapping(target = "images", source = "imageDtos")
    @Mapping(target = "reviews", source = "reviewDtos")
    ItemDto toDto(Item item, List<ImageDto> imageDtos, List<ReviewDto> reviewDtos);



}
