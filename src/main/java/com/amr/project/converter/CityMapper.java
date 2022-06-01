package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    @Mapping(source = "city.id", target = "id")
    @Mapping(source = "city.name", target = "name")
    @Mapping(source = "city.country.name", target = "countryName")
    @Mapping(source = "city.country.id", target = "countryId")
    CityDto toDto(City city);

}
