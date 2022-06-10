package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.webapp.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/2fa")
public class SecurityRestController {
    private final UserService userService;

    @Autowired
    public SecurityRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/enabled")
    public boolean generateSecret(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user.isUsing2FA()) {
            userService.verifyUserBySecret(user);
            return true;
        }
        return false;
    }

    @GetMapping("/afterLogin")
    public void generateSecretAfterLogin(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        user.setActivationCode(String.valueOf(UUID.randomUUID()));
        userService.update(user);
    }
}
