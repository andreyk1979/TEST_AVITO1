package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ImageMapper {
    ImageDto toDto(Image image);

    Image toModel(ImageDto imageDto);
}
