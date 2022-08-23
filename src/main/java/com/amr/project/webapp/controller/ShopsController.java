package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopsService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ShopsController {

    private final UserService userService;
    private final ShopsService shopsService;

    public ShopsController(ShopsService shopsService, UserService userService)
    {
        this.shopsService = shopsService;
        this.userService = userService;
    }

    @GetMapping("/shops")
    public String getContactsPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("activeUser", user);
        model.addAttribute("allShops", shopsService.getAllShops());
        return "shopsPage";
    }
}