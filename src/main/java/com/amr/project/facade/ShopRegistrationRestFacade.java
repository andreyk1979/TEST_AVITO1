package com.amr.project.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ImageService;
import com.amr.project.service.abstracts.ShopService;

@Service
@Transactional
public class ShopRegistrationRestFacade {

    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final ImageService imageService;

    public ShopRegistrationRestFacade(ShopMapper shopMapper, ShopService shopService, ImageService imageService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.imageService = imageService;
    }

    public ResponseEntity<?> registerNewShop(User user, ShopDto shopDto) {
        shopDto.setUserId(user.getId());
        Shop newShop = shopMapper.toModel(shopDto);
        newShop.setModerated(false);
        newShop.setModerateAccept(false);
        shopService.persist(newShop);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
