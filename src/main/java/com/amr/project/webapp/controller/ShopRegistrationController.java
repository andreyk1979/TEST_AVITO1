package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/registration")
public class ShopRegistrationController {

    private ShopMapper shopMapper;
    private ShopService shopService;
    private UserService userService;

    @Autowired
    public ShopRegistrationController(ShopMapper shopMapper, ShopService shopService, UserService userService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.userService = userService;
    }

    @GetMapping()
    public String showRegistrationPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("authUser", user);
        return "shopRegistrationPage";
    }

}

