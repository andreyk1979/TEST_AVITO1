package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.service.abstracts.*;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/shop")
@Api(description ="Данный контроллер возвращает витрину конкретного магазина в виде отдельной HTML страницы")
public class ShopShowcaseController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ShopShowcaseService shopShowcaseService;
    private Map<ItemDto,String> itemsForModel;

    @Autowired
    public ShopShowcaseController(ShopService shopService, ShopMapper shopMapper, ShopShowcaseService shopShowcaseService,ItemMapper itemMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.shopShowcaseService = shopShowcaseService;
        this.itemMapper=itemMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Метод showShopShowcase", notes = "Метод showShopShowcase принимает Id магазина из БД " +
            "и возращает html страницу showcase, которая содержит витрину магазина")
    public String showShopShowcase (@ApiParam("Id магазина из БД") @PathVariable Long id, Model model) throws UnsupportedEncodingException {
        itemsForModel = new HashMap<>();
        ShopDto shop = shopMapper.toDto(shopShowcaseService.getShopForShowcase(id));
        model.addAttribute("singleShop",shop);

        // конвертация лого магазина
        ImageDto image = shop.getLogo();
        byte[] byteLogo = Base64.getEncoder().encode(image.getPicture());
        String logo = new String(byteLogo, "UTF-8");
        model.addAttribute("logo",logo);

        // конвертация лого картинок и их добавление в модель, вместе с товаром
        List<ItemDto> items = itemMapper.toDtoList(shopShowcaseService.getTwoMostPopularItemForShop(id));
        for(ItemDto item : items) {
            ImageDto itemImage = item.getImages().get(0);
            byte[] byteImage = Base64.getEncoder().encode(itemImage.getPicture());
            itemsForModel.put(item,new String(byteImage, "UTF-8"));

        }
        model.addAttribute("popularItems",itemsForModel);
        return "showcase";
    }

}
