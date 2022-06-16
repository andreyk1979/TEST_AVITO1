package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
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
@RequestMapping("/user/{id}/favorite")
@Api(description = "Данный контроллер возвращает страницу, содержащую описание избранных товаров и магазинов пользователя")
public class FavoritePageController {

    private final UserService userService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private Map<ItemDto, String> favoriteItemsForUser;
    private Map<ShopDto, String> favoriteShopsForUser;

    @Autowired
    public FavoritePageController(UserService userService, ShopService shopService, ItemService itemService, ShopMapper shopMapper, ItemMapper itemMapper) {
        this.userService = userService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
    }

    @GetMapping()
    @ApiOperation(value= "Метод showFavoriteForUser", notes= "Метод showFavoriteForUser принимает id пользователя из БД" +
            " и возвращает html страницу favoritePage, которая содержит списки избранных товаров и магазинов пользователя")
    public String showFavoriteForUser(@ApiParam("Id пользователя из БД")@PathVariable Long id, Model model) throws UnsupportedEncodingException {

        List<ItemDto>  itemsList = itemMapper.toDtoList(userService.findById(id).getFavorite().getItems());
        List<ShopDto>  shopsList = shopMapper.toDtoList(userService.findById(id).getFavorite().getShops());

        // конвертация изображений товаров и добавление товаров и их картинки в модель
        favoriteItemsForUser = new HashMap<>();
        for(ItemDto item : itemsList) {
            ImageDto itemImageDto = item.getImages().get(0);
            byte[] byteItemImageDto = Base64.getEncoder().encode(itemImageDto.getPicture());
            favoriteItemsForUser.put(item,new String(byteItemImageDto, "UTF-8"));
        }
        model.addAttribute("items", favoriteItemsForUser);

        // конвертация лого магазинов и добавление магазинов и их лого в модель
        favoriteShopsForUser = new HashMap<>();
        for(ShopDto shop : shopsList){
            ImageDto shopImageDto = shop.getLogo();
            byte[] byteShopImageDto = Base64.getEncoder().encode(shopImageDto.getPicture());
            favoriteShopsForUser.put(shop, new String(byteShopImageDto, "UTF-8"));
        }
        model.addAttribute("shops", favoriteShopsForUser);

        return "favoritePage";
    }
}