package com.amr.project.facade;

import java.security.Principal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class SecurityRestFacade {

    private final UserService userService;

    public SecurityRestFacade(UserService userService) {
        this.userService = userService;
    }

    public boolean generateSecret(String username) {
        User user = userService.findByUsername(username);
        if (user.isUsing2FA()) {
            userService.verifyUserBySecret(user);
            return true;
        }
        return false;
    }

    public void generateSecretAfterLogin(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        user.setActivationCode(String.valueOf(UUID.randomUUID()));
        userService.update(user);
    }
}
