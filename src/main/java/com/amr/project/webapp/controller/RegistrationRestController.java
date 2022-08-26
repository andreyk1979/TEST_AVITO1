package com.amr.project.webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.amr.project.facade.RegistrationRestFacade;
import com.amr.project.model.dto.UserDto;

@Controller
@RequestMapping("/registration")
public class RegistrationRestController {
    private final RegistrationRestFacade registrationRestFacade;

    public RegistrationRestController(RegistrationRestFacade registrationRestFacade) {
        this.registrationRestFacade = registrationRestFacade;
    }

    @PostMapping()
    @ResponseBody
       public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        return registrationRestFacade.addUser(userDto);
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {
        return registrationRestFacade.activate(code);
    }
}
