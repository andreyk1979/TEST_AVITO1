package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.*;
import com.amr.project.model.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopRestController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopRestController(ShopMapper shopMapper, ShopService shopService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
    }

    @GetMapping()
    public List<ShopDto> ShowAllShops() {
        return shopMapper.toDtoList(shopService.findAll());
    }

    @GetMapping("{id}")
    public ShopDto showSingleShop(@PathVariable Long id) {
        return shopMapper.toDto(shopService.findById(id));
    }

    @PostMapping()
    public ShopDto addNewShop(@RequestBody ShopDto shopDto) {
        return shopMapper.toDto(shopService
                .persist(shopMapper.toModel(shopDto)));
    }

    @PutMapping()
    public void editShop(@RequestBody ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        shopService.update(newShop);
    }

    @DeleteMapping("{id}")
    public void deleteShop(@PathVariable Long id) {
        shopService.deleteByIdCascadeIgnore(id);
    }

}
