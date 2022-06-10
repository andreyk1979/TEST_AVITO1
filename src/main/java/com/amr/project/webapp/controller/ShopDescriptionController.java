package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.ShopShowcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop/{id}/description")
public class ShopDescriptionController {

    private final ShopService shopService;
    private final ItemService itemService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private Map<ItemDto,String> itemsForShop;

    @Autowired
    public ShopDescriptionController(ItemService itemService,ShopService shopService, ShopMapper shopMapper, ShopShowcaseService shopShowcaseService, ItemMapper itemMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.itemService = itemService;
    }

    @GetMapping()
    public String showShopShowcase (@PathVariable Long id, Model model) throws UnsupportedEncodingException {
        itemsForShop = new HashMap<>();
        // информация о магазине
        ShopDto shop = shopMapper.toDto(shopService.findById(id));
        model.addAttribute("singleShop",shop);

        // конвертация лого магазина
        ImageDto image = shop.getLogo();
        byte[] byteLogo = Base64.getEncoder().encode(image.getPicture());
        String logo = new String(byteLogo, "UTF-8");
        model.addAttribute("shopLogo",logo);

        // список товаров для магазина
        List<ItemDto> items = itemMapper.toDtoList(itemService.getItemForShop(id));
//        model.addAttribute("items",items);

        // конвертация лого товаров и добавление товаров и их картинки в модель
        for(ItemDto item : items) {
            ImageDto itemImage = item.getImages().get(0);
            byte[] byteImage = Base64.getEncoder().encode(itemImage.getPicture());
            itemsForShop.put(item,new String(byteImage, "UTF-8"));

        }
        model.addAttribute("shopItems",itemsForShop);
        return "shopDescription";
    }
}