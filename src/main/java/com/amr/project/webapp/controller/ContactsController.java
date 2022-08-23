package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ContactsController {
    private final UserService userService;

    public ContactsController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/contacts")
    public String getContactsPage (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("activeUser", user);
        return "contactsPage";
    }
}
