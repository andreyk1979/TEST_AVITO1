package com.amr.project.converter;


import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    UserDto toDto(User user);
    User toUser(UserDto userDto);
    List<UserDto> toDtos(List<User> users);
}
