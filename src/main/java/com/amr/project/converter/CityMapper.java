package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "city.id", target = "id")
    @Mapping(source = "city.name", target = "name")
    @Mapping(source = "city.country.name", target = "countryName")
    @Mapping(source = "city.country.id", target = "countryId")
    CityDto toDto(City city);
}
