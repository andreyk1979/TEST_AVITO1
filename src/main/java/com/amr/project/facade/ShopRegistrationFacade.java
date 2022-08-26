package com.amr.project.facade;

import java.security.Principal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class ShopRegistrationFacade {

    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final UserService userService;

    public ShopRegistrationFacade(ShopMapper shopMapper, ShopService shopService, UserService userService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.userService = userService;
    }

    public String showRegistrationPage(Model model, Principal principal) {
        model.addAttribute("shopToRegister", new ShopDto());
        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
            // Замена первой буквы имени на заглавную для корректного отображения в приветствии на фронте.
            user.setUsername(user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1));
        }
        model.addAttribute("activeUser", user);
        return "registrationPage";
    }

    public String addUser(@ModelAttribute("attribute") ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        newShop.setModerated(false);
        newShop.setModerateAccept(false);
        shopService.persist(newShop);

        return "redirect:/shop/registration";
    }
}
