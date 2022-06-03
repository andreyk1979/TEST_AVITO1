package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopShowcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopShowcaseServiceImpl implements ShopShowcaseService {

    private ItemDao itemDao;
    private ShopDao shopDao;

    @Autowired
    public ShopShowcaseServiceImpl(ItemDao itemDao, ShopDao shopDao) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
    }

    @Override
    @Transactional
    public List<Item> getTwoMostPopularItemForShop(Long shopId) {
        return itemDao.getTwoMostPopularItemForShop(shopId);
    }

    @Override
    @Transactional
    public Shop getShopForShowcase(Long shopId) {
        return shopDao.findById(shopId);
    }
}
