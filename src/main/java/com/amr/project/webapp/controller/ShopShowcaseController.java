package com.amr.project.webapp.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.ShopShowcaseFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/shop")
@Api(description ="Данный контроллер возвращает витрину конкретного магазина в виде отдельной HTML страницы")
public class ShopShowcaseController {

    private final ShopShowcaseFacade shopShowcaseFacade;

    public ShopShowcaseController(ShopShowcaseFacade shopShowcaseFacade) {
        this.shopShowcaseFacade = shopShowcaseFacade;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Метод showShopShowcase", notes = "Метод showShopShowcase принимает Id магазина из БД " +
            "и возращает html страницу showcase, которая содержит витрину магазина")
    public String showShopShowcase (@ApiParam("Id магазина из БД") @PathVariable Long id, Model model) throws UnsupportedEncodingException {
        return shopShowcaseFacade.showShopShowcase(id, model);
    }

}
