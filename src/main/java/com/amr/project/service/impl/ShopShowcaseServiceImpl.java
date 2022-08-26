package com.amr.project.service.impl;

import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.ShopShowcaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ShopShowcaseServiceImpl implements ShopShowcaseService {

    private ItemService itemService;
    private ShopService shopService;

    public ShopShowcaseServiceImpl(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @Override
    @Transactional
    public List<Item> getTwoMostPopularItemForShop(Long shopId) {
        return itemService.getTwoMostPopularItemForShop(shopId);
    }

    @Override
    @Transactional
    public Shop getShopForShowcase(Long shopId) {
        return shopService.findById(shopId);
    }
}
