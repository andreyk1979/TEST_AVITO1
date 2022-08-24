package com.amr.project.webapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.ShopRegistrationFacade;
import com.amr.project.model.dto.ShopDto;

@Controller
@RequestMapping("/shop/registration")
public class ShopRegistrationController {

    private final ShopRegistrationFacade shopRegistrationFacade;

    public ShopRegistrationController(ShopRegistrationFacade shopRegistrationFacade) {
        this.shopRegistrationFacade = shopRegistrationFacade;
    }

    @GetMapping()
    public String showRegistrationPage(Model model, Principal principal) {
        return shopRegistrationFacade.showRegistrationPage(model, principal);
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("attribute") ShopDto shopDto) {
        return shopRegistrationFacade.addUser(shopDto);
    }
}

