package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.MainPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Base64;

@Controller
public class MainPageController {

    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute(mainPageService.getMainPageDto());
        return "index";
    }

    @GetMapping("/sales")
    public String getSalesHistory(@RequestParam Long id, Model model) {
        model.addAttribute("shopId", id);
        return "salesHistory";
    }
}
