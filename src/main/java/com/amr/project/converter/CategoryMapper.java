package com.amr.project.converter;

import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    CategoryDto toDto(Category Category);

    Category toModel(CategoryDto categoryDto);

    List<CategoryDto> toDtoList(List<Category> categoryList);

    List<Category> toModelList(List<CategoryDto> categoryDtoList);
}
