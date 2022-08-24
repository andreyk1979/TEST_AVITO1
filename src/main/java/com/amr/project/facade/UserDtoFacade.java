package com.amr.project.facade;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class UserDtoFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserDtoFacade(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserDto createUser(UserDto userDto) {
        return userMapper.toDto(userService.persist(userMapper.toUser(userDto)));
    }

    public void updateUser(UserDto userDto) {
        User newUser = userMapper.toUser(userDto);
        userService.update(newUser);
    }

    public void deleteUser(Long id) {
        userService.deleteByIdCascadeEnable(id);
    }

    public List<UserDto> allUsers() {
        return userMapper.toDtos(userService.findAll());
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    public UserDto getUserByName(Principal principal) {
        return userMapper.toDto(userService.findByUsername(principal.getName()));
    }
}
