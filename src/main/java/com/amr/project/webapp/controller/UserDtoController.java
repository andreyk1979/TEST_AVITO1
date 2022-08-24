package com.amr.project.webapp.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.UserDtoFacade;
import com.amr.project.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
@Api(description ="REST контроллер для работы с пользователями (model-entity-User)")
public class UserDtoController {

    private final UserDtoFacade userDtoFacade;

    public UserDtoController(UserDtoFacade userDtoFacade) {
        this.userDtoFacade = userDtoFacade;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод createUser", notes = "Метод createUser принимает UserDto из тела request " +
            "сохраняет его в БД и возвращает UserDto созданного пользователя" )
    public UserDto createUser(@ApiParam("UserDto для добавления пользователя в БД") @RequestBody UserDto userDto) {
        return userDtoFacade.createUser(userDto);
    }
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод updateUser", notes = "Метод updateUser принимает UserDto из тела request для имеющегося в БД пользователя" +
            "изменяет его представление в БД. Ничего не возвращает" )
    public void updateUser(@ApiParam("UserDto для изменения пользователя в БД") @RequestBody UserDto userDto) {
        userDtoFacade.updateUser(userDto);
    }
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод deleteUser", notes = "Метод deleteUser принимает Id для имеющегося в БД пользователя" +
            " и удаляет его. Ничего не возвращает" )
    public void deleteUser(@ApiParam("Id пользователя из БД, которого требуется удалить") @PathVariable("id") Long id) {
        userDtoFacade.deleteUser(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод allShops", notes = "Метод allShops возвращает List UserDto - " +
            "список всех пользователей из БД" )
    public List<UserDto> allUsers() {
        return userDtoFacade.allUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Метод getUserById", notes = "Метод getUserById принимает Id магазина из БД " +
            "возвращает UserDto" )
    public UserDto getUserById(@ApiParam("Id пользователя из БД") @PathVariable Long id) {
        return userDtoFacade.getUserById(id);
    }

    @ApiOperation(value = "Метод getUserByName", notes = "Метод getUserByName принимает Principal магазина и " +
            "возвращает UserDto" )
    @GetMapping("/users/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByName(Principal principal) {
        return userDtoFacade.getUserByName(principal);
    }
}
