package com.amr.project.webapp.controller;

import com.amr.project.model.dto.UserDto;
import com.amr.project.service.abstracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationRestController {

    private final UserService userService;

    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        if (!userService.addUser(userDto)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {
        userService.activate(code);
        return "redirect:/";
    }
}
