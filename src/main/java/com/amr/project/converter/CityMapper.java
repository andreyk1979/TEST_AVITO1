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

    //TODO возврат моделей нужно реализовать @Mapping если нужен
    City toModel(CityDto cityDto);
}
