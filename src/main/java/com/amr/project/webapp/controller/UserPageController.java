package com.amr.project.webapp.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.UserPageFacade;
import com.amr.project.model.entity.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/user")
@Tag(name = "Контроллер страницы юзера", description = "Данный контроллер возвращает страницу, содержащую описание пользователя")
public class UserPageController {

    private final UserPageFacade userFacade;

    public UserPageController(UserPageFacade userFacade) {
        this.userFacade = userFacade;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Метод showUserPage", notes = "Метод showUserPage принимает id пользователя из БД" +
            " и возвращает html страницу userPage, которая содержит описание пользователя: его личные данные, фотографию, список заказов и магазинов")
    public String showUserPage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {
        return userFacade.showUserPage(id, model, user);
    }
}
