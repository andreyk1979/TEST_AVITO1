package com.amr.project.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.amr.project.facade.ShopsFacade;
import com.amr.project.model.entity.User;

@Controller
public class ShopsController {

    private final ShopsFacade shopsFacade;

    public ShopsController(ShopsFacade shopsFacade) {
        this.shopsFacade = shopsFacade;
    }

    @GetMapping("/shops")
    public String getContactsPage(Model model, @AuthenticationPrincipal User user) {
        return shopsFacade.getContactsPage(model, user);
    }
}