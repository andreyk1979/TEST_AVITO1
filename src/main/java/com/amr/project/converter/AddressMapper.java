package com.amr.project.converter;

import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CountryMapper.class})
public interface AddressMapper {
    @Mapping(target = "cityId", source = "address.city.id")
    @Mapping(target = "city", source = "address.city.name")
    @Mapping(target = "countryId", source = "address.city.country.id")
    @Mapping(target = "country", source = "address.city.country.name")
    AddressDto toDto (Address address);

    @Mapping(target = "city.id", source = "addressDto.cityId")
    @Mapping(target = "city.name", source = "addressDto.city")
    @Mapping(target = "city.country.id", source = "addressDto.countryId")
    @Mapping(target = "city.country.name", source = "addressDto.country")
    Address toModel (AddressDto addressDto);
}
