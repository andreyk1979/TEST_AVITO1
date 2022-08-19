package com.amr.project.webapp.controller;

import com.amr.project.converter.BasketMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.BasketService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/basket")
public class BasketController {

    BasketService basketService;
    UserService userService;
    UserMapper userMapper;
    BasketMapper basketMapper;
    @Autowired
    public BasketController(BasketService basketService, UserService userService, UserMapper userMapper, BasketMapper basketMapper) {
        this.basketService = basketService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.basketMapper = basketMapper;
    }
    @ApiOperation(value = "Метод getBasket",
            notes = "Метод возращает basket.html по id пользователя - корзина покупателя")
    @GetMapping
    public String getBasket(Model model, @AuthenticationPrincipal User user) {
        if (user == null) { // todo makeev - реализовать логику работы с временной корзиной, если необходимо чтобы неиндентифицированный пользователь мог ей пользоваться
            return "redirect:/";
        }
        UserDto userDto = userMapper.toDto(user);
        BasketDto basketDto = basketMapper.toDto(basketService.findById(user.getId()));
        model.addAttribute("userDto", userDto);
        model.addAttribute("basketDto", basketDto);
        model.addAttribute("activeUser", user);
        return "/basket";
    }
}
