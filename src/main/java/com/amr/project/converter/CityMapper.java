package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {
    @Mapping(target = "countryName", source = "country.name")
    @Mapping(target = "countryId", source = "country.id")
    CityDto toDto(City city);

    City toModel(CityDto cityDtoDto);
}
