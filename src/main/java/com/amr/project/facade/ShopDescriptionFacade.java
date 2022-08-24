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

@Service
@Transactional
public class ShopDescriptionFacade {

    private final ShopService shopService;
    private final ItemService itemService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private Map<ItemDto,String> itemsForShop;

    public ShopDescriptionFacade(ShopService shopService, ItemService itemService, ShopMapper shopMapper, ItemMapper itemMapper) {
        this.shopService = shopService;
        this.itemService = itemService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
    }

    public String showShopShowcase (Long id, Model model) throws UnsupportedEncodingException {
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
