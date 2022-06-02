package com.amr.project.service.abstracts;


import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ShopShowcaseService {
    List<Item> getTwoMostPopularItemForShop (Long shopId);

    Shop getShopForShowcase(Long shopId);
}
