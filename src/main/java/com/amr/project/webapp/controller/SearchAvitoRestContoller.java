package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchAvitoRestContoller {
    protected final ReadWriteService<Item, Long> readWriteService;
    protected final ReadWriteService<Shop, Long> readWriteServiceShop;

    public SearchAvitoRestContoller(ReadWriteService<Item, Long> readWriteService, ReadWriteService<Shop, Long> readWriteServiceShop) {
        this.readWriteService = readWriteService;
        this.readWriteServiceShop = readWriteServiceShop;
    }


    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name) {
        List<Item> item = readWriteService.findAll();
        List<Shop> shops = readWriteServiceShop.findAll();
        List<ItemDto> dtoItemList = new ArrayList<>();
        List<ShopDto> dtoShopList = new ArrayList<>();
        for (Item nameItem : item) {
            if (nameItem.getName().contains(name.toLowerCase())) {
                ItemDto itemDto = ItemMapper.INSTANCE.toDto(nameItem);
                dtoItemList.add(itemDto);
            }
        }
        for (Shop shop : shops) {
            if (shop.getName().contains(name.toLowerCase())) {
                ShopDto shopDto = ShopMapper.INSTANCE.toDto(shop);
                dtoShopList.add(shopDto);
            }
        }
        if (dtoItemList.isEmpty() && dtoShopList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ItemShopDto(dtoShopList, dtoItemList), HttpStatus.OK);
    }
}
