package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ImageService;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shop/create")
public class ShopRegistrationRestController {

    private ShopMapper shopMapper;
    private ShopService shopService;
    private ImageService imageService;

    @Autowired
    public ShopRegistrationRestController(ShopMapper shopMapper, ShopService shopService, ImageService imageService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.imageService = imageService;
    }

    // TODO перед сохранением в БД отправлять запрос на регистрацию модератору
    @PostMapping()
    public ResponseEntity<?> registerNewShop(@AuthenticationPrincipal User user, @RequestBody ShopDto shopDto) {
        shopDto.setUserId(user.getId());
        Shop newShop = shopMapper.toModel(shopDto);
        newShop.setModerated(false);
        newShop.setModerateAccept(false);
        shopService.persist(newShop);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
