package com.amr.project.webapp.controller;


import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(description ="REST контроллер для работы с пользователями (model-entity-User)")
public class UserDtoController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserDtoController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод createUser", notes = "Метод createUser принимает UserDto из тела request " +
            "сохраняет его в БД и возвращает UserDto созданного пользователя" )
    public UserDto createUser(@ApiParam("UserDto для добавления пользователя в БД") @RequestBody UserDto userDto) {
        return userMapper.toDto(userService.persist(userMapper.toUser(userDto)));
    }
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод updateUser", notes = "Метод updateUser принимает UserDto из тела request для имеющегося в БД пользователя" +
            "изменяет его представление в БД. Ничего не возвращает" )
    public void updateUser(@ApiParam("UserDto для изменения пользователя в БД") @RequestBody UserDto userDto) {
        User newUser = userMapper.toUser(userDto);
        userService.update(newUser);
    }
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод deleteUser", notes = "Метод deleteUser принимает Id для имеющегося в БД пользователя" +
            " и удаляет его. Ничего не возвращает" )
    public void deleteUser(@ApiParam("Id пользователя из БД, которого требуется удалить") @PathVariable("id") Long id) {
        userService.deleteByIdCascadeEnable(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод allShops", notes = "Метод allShops возвращает List UserDto - " +
            "список всех пользователей из БД" )
    public List<UserDto> allUsers() {
        return userMapper.toDtos(userService.findAll());
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод getUserById", notes = "Метод getUserById принимает Id магазина из БД " +
            "возвращает UserDto" )
    public UserDto getUserById(@ApiParam("Id пользователя из БД") @PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @ApiOperation(value = "Метод getUserByName", notes = "Метод getUserByName принимает Principal магазина и " +
            "возвращает UserDto" )
    @GetMapping("/users/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByName(Principal principal) {
        return userMapper.toDto(userService.findByUsername(principal.getName()));
    }
}
