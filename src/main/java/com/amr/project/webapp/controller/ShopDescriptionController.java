package com.amr.project.webapp.controller;

import com.amr.project.facade.ShopDescriptionFacade;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/shop/{id}/description")
@Api(description ="Данный контроллер возвращает страницу, содержащую описание конкретного магазина: название, контакты и товары")
public class ShopDescriptionController {

    private final ShopDescriptionFacade shopDescriptionFacade;

    public ShopDescriptionController(ShopDescriptionFacade shopDescriptionFacade) {
        this.shopDescriptionFacade = shopDescriptionFacade;
    }

    @GetMapping()
    @ApiOperation(value = "Метод showShopShowcase", notes = "Метод showShopShowcase принимает Id магазина из БД " +
            "и возращает html страницу shopDescription, которая содержит необходимую информацию")
    public String showShopShowcase (@ApiParam("Id магазина из БД") @PathVariable Long id, Model model) throws UnsupportedEncodingException {
        return shopDescriptionFacade.showShopShowcase(id, model);
    }
}