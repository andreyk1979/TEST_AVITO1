package com.amr.project.webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ShopRegistrationRestFacade;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.User;


@RestController
@RequestMapping("/shop/create")
public class ShopRegistrationRestController {

    private final ShopRegistrationRestFacade shopRegistrationRestFacade;

    public ShopRegistrationRestController(ShopRegistrationRestFacade shopRegistrationRestFacade) {
        this.shopRegistrationRestFacade = shopRegistrationRestFacade;
    }

    // TODO перед сохранением в БД отправлять запрос на регистрацию модератору
    @PostMapping()
    public ResponseEntity<?> registerNewShop(@AuthenticationPrincipal User user, @RequestBody ShopDto shopDto) {
        return shopRegistrationRestFacade.registerNewShop(user, shopDto);
    }
}
