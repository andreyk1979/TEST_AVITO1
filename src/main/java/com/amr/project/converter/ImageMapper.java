package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.entity.Image;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {Base64.class})
public interface ImageMapper {
    @Mapping(target = "pictureBase64", expression = "java(" +
            "new String(Base64.getEncoder().encode(image.getPicture()), \"UTF-8\"))")
    ImageDto toDto(Image image) throws UnsupportedEncodingException;

    //TODO возврат моделей нужно реализовать @Mapping если нужен
    Image toModel(ImageDto imageDto);
}
