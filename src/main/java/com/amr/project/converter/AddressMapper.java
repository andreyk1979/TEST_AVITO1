//package com.amr.project.converter;
//
//import com.amr.project.model.dto.AddressDto;
//import com.amr.project.model.entity.Address;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface AddressMapper {
//    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
//
//    @Mapping(target = "id", source = "address.id")
//    @Mapping(target = "cityId", source = "address.city.id")
//    @Mapping(target = "city", source = "address.city.name")
//    @Mapping(target = "countryId", source = "address.city.country.id")
//    @Mapping(target = "country", source = "address.city.country.name")
//    AddressDto toDto(Address address);
//}
