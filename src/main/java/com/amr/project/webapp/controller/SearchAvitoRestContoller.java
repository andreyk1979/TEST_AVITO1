package com.amr.project.webapp.controller;

import com.amr.project.converter.*;
import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
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

    private final ReadWriteService<Item, Long> serviceImplItem;
    private final ReadWriteService<Shop, Long> serviceImplShop;

    public SearchAvitoRestContoller(ReadWriteService<Item, Long> serviceImplItem, ReadWriteService<Shop, Long> serviceImplShop) {
        this.serviceImplItem = serviceImplItem;
        this.serviceImplShop = serviceImplShop;
    }


    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name) {
        List<Item> item = serviceImplItem.findAll();
        List<Shop> shops = serviceImplShop.findAll();
        List<ItemDto> dtoItemList = new ArrayList<>();
        List<ShopDto> dtoShopList = new ArrayList<>();
        for (Item nameItem : item) {
            if (nameItem.getName().contains(name.toLowerCase())) {
                List<Image> imageList = nameItem.getImages();
                List<Review> reviewList = nameItem.getReviews();
                List<ImageDto> imageDtos = new ArrayList<>();
                List<ReviewDto> reviewDtos = new ArrayList<>();
                for (Image image : imageList) {
                    imageDtos.add(ImageMapper.INSTANCE.toDto(image));
                }
                for (Review review : reviewList) {
                    reviewDtos.add(ReviewMapper.INSTANCE.toDto(review));
                }
                ItemDto itemDto = ItemMapper.INSTANCE.toDto(nameItem, imageDtos, reviewDtos);
                dtoItemList.add(itemDto);
            }
        }
        for (Shop shop : shops) {
            if (shop.getName().contains(name.toLowerCase())) {
                List<Long> coupons = new ArrayList<>();
                for (Coupon coupon : shop.getCoupons()) {
                    coupons.add(coupon.getId());
                }
                ShopDto shopDto = ShopMapper.INSTANCE
                        .toDto(shop, CityMapper.INSTANCE.toDto(shop.getAddress().getCity()), coupons);
                dtoShopList.add(shopDto);
            }
        }
        if (dtoItemList.isEmpty() && dtoShopList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ItemShopDto(dtoShopList, dtoItemList), HttpStatus.OK);
    }
}
