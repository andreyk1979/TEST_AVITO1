package com.amr.project.webapp.controller;


import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserDtoController {
    private ReadWriteService readWriteService;
    private UserMapper userMapper;
    @Autowired
    public UserDtoController(ReadWriteService readWriteService, UserMapper userMapper) {
        this.readWriteService = readWriteService;
        this.userMapper = userMapper;
    }

    @PostMapping("/user")
    public ResponseEntity<List<UserDto>> createUser(@RequestBody UserDto userDto){
        readWriteService.persist(userMapper.toUser(userDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/users")
    public ResponseEntity<UserDto> updateUserDto(@RequestBody UserDto userDto) {
        readWriteService.update(userMapper.toUser(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserDto(@PathVariable("id") int id) {
        readWriteService.deleteByIdCascadeEnable(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
