package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @Autowired
    private MainPageService mainPageService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute(mainPageService.getMainPageDto());
        return "index";
    }

}
