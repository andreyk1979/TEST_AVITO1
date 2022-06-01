package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.service.abstracts.ItemShopSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemShopSearchServiceImpl implements ItemShopSearchService {

    protected final ItemDao itemDao;
    protected final ShopDao shopDao;
    protected final ItemMapper itemMapper;
    protected final ShopMapper shopMapper;

    public ItemShopSearchServiceImpl(ItemDao itemDao, ShopDao shopDao, ItemMapper itemMapper, ShopMapper shopMapper) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
    }

    @Override
    @Transactional
    public ItemShopDto getItemShopDto(String name) {
        return new ItemShopDto(shopMapper.toDtoList(shopDao.findShopList(name)),
                itemMapper.toDtoList(itemDao.findItemList(name)));
    }
}
