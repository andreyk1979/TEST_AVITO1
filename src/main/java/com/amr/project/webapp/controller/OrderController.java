package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/order")
public class OrderController {

    @PostMapping
    public String createOrder(@AuthenticationPrincipal User user, Model model) {
        // Заглушка
        model.addAttribute("activeUser", user);
        return "/orderPage";
    }
}
