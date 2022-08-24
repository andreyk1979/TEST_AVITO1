package com.amr.project.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MainPageService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class MainPageFacade {

    private final MainPageService mainPageService;
    private final UserService userService;

    public MainPageFacade(MainPageService mainPageService, UserService userService) {
        this.mainPageService = mainPageService;
        this.userService = userService;
    }

    public String getMainPage(Model model, User user) {
        model.addAttribute(mainPageService.getMainPageDto());
        model.addAttribute("activeUser", user);
        return "index";
    }

    public String getSalesHistory(Long id, Model model) {
        model.addAttribute("shopId", id);
        return "salesHistory";
    }
}
