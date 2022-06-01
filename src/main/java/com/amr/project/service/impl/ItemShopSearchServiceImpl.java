package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.service.abstracts.ItemShopSearchService;
import org.springframework.stereotype.Service;

@Service
public class ItemShopSearchServiceImpl implements ItemShopSearchService {
    protected final ItemDao itemDao;
    protected final ShopDao shopDao;

    public ItemShopSearchServiceImpl(ItemDao itemDao, ShopDao shopDao) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
    }

    @Override
    public ItemShopDto getItemShopDto(String name) {
        return new ItemShopDto(shopDao.findShopList(name), itemDao.findItemList(name));
    }
}
