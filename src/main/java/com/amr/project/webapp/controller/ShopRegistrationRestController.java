package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addShopOnUserPage")
public class ShopRegistrationRestController {

    private ShopMapper shopMapper;
    private ShopService shopService;

    @Autowired
    public ShopRegistrationRestController(ShopMapper shopMapper, ShopService shopService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
    }

    @PostMapping()
    public ShopDto registerNewShop(@RequestBody ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        newShop.setModerated(false);
        newShop.setModerateAccept(false);
        shopService.persist(newShop);

        return shopMapper.toDto(newShop);
    }
}

