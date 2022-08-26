package com.amr.project.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import com.amr.project.model.dto.UserDto;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class RegistrationRestFacade {

    private final UserService userService;

    public RegistrationRestFacade(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<?> addUser(UserDto userDto) {
        if(!userService.addUser(userDto)) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String activate(@PathVariable String code) {
        userService.activate(code);
        return "redirect:/";
    }
}
