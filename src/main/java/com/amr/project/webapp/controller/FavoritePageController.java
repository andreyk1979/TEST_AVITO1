package com.amr.project.webapp.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.FavoritePageFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/user/{id}/favorite")
@Api(description = "Данный контроллер возвращает страницу, содержащую описание избранных товаров и магазинов пользователя")
public class FavoritePageController {

    private final FavoritePageFacade favoritePageFacade;

    public FavoritePageController(FavoritePageFacade favoritePageFacade) {
        this.favoritePageFacade = favoritePageFacade;
    }

    @GetMapping()
    @ApiOperation(value= "Метод showFavoriteForUser", notes= "Метод showFavoriteForUser принимает id пользователя из БД" +
            " и возвращает html страницу favoritePage, которая содержит списки избранных товаров и магазинов пользователя")
    public String showFavoriteForUser(@ApiParam("Id пользователя из БД")@PathVariable Long id, Model model) throws UnsupportedEncodingException {
        return favoritePageFacade.showFavoriteForUser(id, model);
    }
}