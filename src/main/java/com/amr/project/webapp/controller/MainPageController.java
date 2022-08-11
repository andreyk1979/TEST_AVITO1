package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MainPageService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MainPageController {

    private final MainPageService mainPageService;
    private final UserService userService;

    public MainPageController(MainPageService mainPageService, UserService userService) {
        this.mainPageService = mainPageService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getMainPage(Model model, Principal principal) {
        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
            // Замена первой буквы имени на заглавную для корректного отображения в приветствии на фронте.
            user.setUsername(user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1));
        }
        model.addAttribute(mainPageService.getMainPageDto());
        model.addAttribute("activeUser", user);
        return "index";
    }

    @GetMapping("/sales")
    public String getSalesHistory(@RequestParam Long id, Model model) {
        model.addAttribute("shopId", id);
        return "salesHistory";
    }
}
