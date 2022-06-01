package com.amr.project.converter;

import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReviewMapper {

    @Mapping(target = "itemId", source = "review.item.id")
    @Mapping(target = "userId", source = "review.user.id")
    @Mapping(target = "shopId", source = "review.shop.id")
    ReviewDto toDto(Review review);

}
