package com.amr.project.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.amr.project.facade.MainPageFacade;
import com.amr.project.model.entity.User;

@Controller
public class MainPageController {

    private final MainPageFacade mainPageFacade;

    public MainPageController(MainPageFacade mainPageFacade) {
        this.mainPageFacade = mainPageFacade;
    }

    @GetMapping("/")
    public String getMainPage(Model model, @AuthenticationPrincipal User user) {
        return mainPageFacade.getMainPage(model, user);
    }

    @GetMapping("/sales")
    public String getSalesHistory(@RequestParam Long id, Model model) {
        return mainPageFacade.getSalesHistory(id, model);
    }
}
