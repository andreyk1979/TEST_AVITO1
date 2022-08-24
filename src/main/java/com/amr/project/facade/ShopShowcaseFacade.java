package com.amr.project.facade;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.ShopShowcaseService;

import io.swagger.annotations.ApiParam;

@Service
@Transactional
public class ShopShowcaseFacade {
    private final ShopService shopService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ShopShowcaseService shopShowcaseService;
    private Map<ItemDto,String> itemsForModel;

    public ShopShowcaseFacade(ShopService shopService,
                              ShopMapper shopMapper,
                              ItemMapper itemMapper,
                              ShopShowcaseService shopShowcaseService) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.shopShowcaseService = shopShowcaseService;
    }

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
