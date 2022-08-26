package com.amr.project.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopsService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class ShopsFacade {

    private final UserService userService;
    private final ShopsService shopsService;

    public ShopsFacade(UserService userService, ShopsService shopsService) {
        this.userService = userService;
        this.shopsService = shopsService;
    }

    public String getContactsPage(Model model, User user) {
        model.addAttribute("activeUser", user);
        model.addAttribute("allShops", shopsService.getAllShops());
        return "shopsPage";
    }
}
