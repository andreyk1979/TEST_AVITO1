package com.amr.project.converter;

import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {ZoneId.class, Date.class})
public interface ReviewMapper {
    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "date",
            expression = "java(review.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())")
    ReviewDto toDto(Review review);

    Review toModel(ReviewDto reviewDto);

}
