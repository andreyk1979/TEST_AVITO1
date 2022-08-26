package com.amr.project.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.BasketFacade;
import com.amr.project.model.entity.User;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final BasketFacade basketFacade;

    public BasketController(BasketFacade basketFacade) {
        this.basketFacade = basketFacade;
    }

    @ApiOperation(value = "Метод getBasket",
            notes = "Метод возращает basket.html по id пользователя - корзина покупателя")
    @GetMapping
    public String getBasket(Model model, @AuthenticationPrincipal User user) {
        return basketFacade.getBasket(model, user);
    }
}
