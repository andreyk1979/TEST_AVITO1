package com.amr.project.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.converter.BasketMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.BasketService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class BasketFacade {

    private final BasketService basketService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final BasketMapper basketMapper;

    public BasketFacade(BasketService basketService,
                        UserService userService,
                        UserMapper userMapper,
                        BasketMapper basketMapper) {
        this.basketService = basketService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.basketMapper = basketMapper;
    }

    public String getBasket(Model model, User user) {
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
