package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.*;
import com.amr.project.service.abstracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopShowcaseController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ShopShowcaseService shopShowcaseService;

    @Autowired
    public ShopShowcaseController(ShopService shopService, ShopMapper shopMapper, ShopShowcaseService shopShowcaseService, ItemMapper itemMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.shopShowcaseService = shopShowcaseService;
        this.itemMapper = itemMapper;
    }

    @GetMapping("/{id}")
    public String showShopShowcase(@PathVariable Long id, Model model) throws UnsupportedEncodingException {
        ShopDto shop = shopMapper.toDto(shopShowcaseService.getShopForShowcase(id));
        model.addAttribute("singleShop", shop);

        ImageDto image = shop.getLogo();
        byte[] byteLogo = Base64.getEncoder().encode(image.getPicture());
        String logo = new String(byteLogo, "UTF-8");
        model.addAttribute("logo", logo);

        List<ItemDto> items = itemMapper.toDtoList(shopShowcaseService.getTwoMostPopularItemForShop(id));
        model.addAttribute("popularItems", items);

        return "index";
    }

}