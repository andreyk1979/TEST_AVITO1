package com.amr.project.facade;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class FavoritePageFacade {

    private final UserService userService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;

    private Map<ItemDto, String> favoriteItemsForUser;
    private Map<ShopDto, String> favoriteShopsForUser;

    public FavoritePageFacade(UserService userService,
                              ShopService shopService,
                              ItemService itemService,
                              ShopMapper shopMapper,
                              ItemMapper itemMapper) {
        this.userService = userService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
    }

    public String showFavoriteForUser(Long id, Model model) throws UnsupportedEncodingException {

        List<ItemDto> itemsList = itemMapper.toDtoList(userService.findById(id).getFavorite().getItems());
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
