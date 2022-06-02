package com.amr.project.converter;


import com.amr.project.model.dto.UserDto;

import com.amr.project.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDto toDto(User user);

    User toModel(UserDto userDto);
}
