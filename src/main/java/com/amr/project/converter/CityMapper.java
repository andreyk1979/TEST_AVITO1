package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.id", target = "countryId")
    CityDto toDto(City city);

    City toModel(CityDto cityDto);
}
