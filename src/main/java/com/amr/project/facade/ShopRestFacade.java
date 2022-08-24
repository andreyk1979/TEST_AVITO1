package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;

@Service
@Transactional
public class ShopRestFacade {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    public ShopRestFacade(ShopService shopService, ShopMapper shopMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
    }

    public List<ShopDto> ShowAllShops() {
        return shopMapper.toDtoList(shopService.findAll());
    }

    public ShopDto showSingleShop(Long id) {
        return shopMapper.toDto(shopService.findById(id));
    }

    public ShopDto addNewShop(ShopDto shopDto) {
        return shopMapper.toDto(shopService
                .persist(shopMapper.toModel(shopDto)));
    }

    public void editShop(ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        shopService.update(newShop);
    }

    public void deleteShop(Long id) {
        shopService.deleteByIdCascadeIgnore(id);
    }
}
