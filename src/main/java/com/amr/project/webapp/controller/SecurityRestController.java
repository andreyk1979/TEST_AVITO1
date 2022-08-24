package com.amr.project.webapp.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.SecurityRestFacade;

@RestController
@RequestMapping("/2fa")
public class SecurityRestController {
    private final SecurityRestFacade securityRestFacade;

    public SecurityRestController(SecurityRestFacade securityRestFacade) {
        this.securityRestFacade = securityRestFacade;
    }

    @GetMapping("/enabled")
    public boolean generateSecret(@RequestParam String username) {
        return securityRestFacade.generateSecret(username);
    }

    @GetMapping("/afterLogin")
    public void generateSecretAfterLogin(Principal principal) {
        securityRestFacade.generateSecretAfterLogin(principal);
    }
}
