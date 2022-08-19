package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MainPageService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String getMainPage(Model model, @AuthenticationPrincipal User user) {
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
