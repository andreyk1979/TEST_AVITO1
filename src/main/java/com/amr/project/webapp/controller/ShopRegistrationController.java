package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/registration")
public class ShopRegistrationController {

    private ShopMapper shopMapper;
    private ShopService shopService;

    @Autowired
    public ShopRegistrationController(ShopMapper shopMapper, ShopService shopService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
    }

    @GetMapping()
    public String showRegistrationPage(Model model) {
        model.addAttribute("shopToRegister", new ShopDto());

        return "registrationPage";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("attribute") ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        newShop.setModerated(false);
        newShop.setModerateAccept(false);
        shopService.persist(newShop);

        return "redirect:/shop/registration";
    }
}

