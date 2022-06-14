package com.amr.project.converter;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AddressMapper.class, ImageMapper.class, ReviewMapper.class},
        imports = {Calendar.class, Date.class, LocalDate.class, ZoneId.class, UUID.class})
public interface UserMapper {

    UserDto toDto(User user);
    @Mapping(target = "userInfo.phone", source = "phone")
    @Mapping(target = "userInfo.firstName", source = "firstName")
    @Mapping(target = "userInfo.lastName", source = "lastName")
    @Mapping(target = "userInfo.age", source = "age")
    @Mapping(target = "userInfo.gender", source = "gender")
    @Mapping(target = "userInfo.birthday", source = "birthday")
    @Mapping(target = "secret", defaultExpression = "java(UUID.randomUUID().toString())")
    User toUser(UserDto userDto);
    List<UserDto> toDtos(List<User> users);
    }
