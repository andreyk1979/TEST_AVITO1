package com.amr.project.webapp.controller;


import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserDtoController {
    private ReadWriteService<User,Long> readWriteService;
    private UserMapper userMapper;
    @Autowired
    public UserDtoController(ReadWriteService<User, Long> readWriteService, UserMapper userMapper) {
        this.readWriteService = readWriteService;
        this.userMapper = userMapper;
    }
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userMapper.toDto(readWriteService.persist(userMapper.toUser(userDto)));
    }
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserDto userDto) {
        User newUser = userMapper.toUser(userDto);
        readWriteService.update(newUser);
    }
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        readWriteService.deleteByIdCascadeEnable(id);
    }
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> allUsers() {
        return userMapper.toDtos(readWriteService.findAll());
    }
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(readWriteService.findById(id));
    }
}
