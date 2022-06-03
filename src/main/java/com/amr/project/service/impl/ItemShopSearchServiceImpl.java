package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ItemSearchDao;
import com.amr.project.dao.abstracts.ShopSearchDao;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.service.abstracts.ItemShopSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemShopSearchServiceImpl implements ItemShopSearchService {

    protected final ItemSearchDao itemSearchDao;
    protected final ShopSearchDao shopSearchDao;
    protected final ItemMapper itemMapper;
    protected final ShopMapper shopMapper;

    public ItemShopSearchServiceImpl(ItemSearchDao itemSearchDao, ShopSearchDao shopSearchDao, ItemMapper itemMapper, ShopMapper shopMapper) {
        this.itemSearchDao = itemSearchDao;
        this.shopSearchDao = shopSearchDao;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
    }

    @Override
    @Transactional
    public ItemShopDto getItemShopDto(String name) {
        return new ItemShopDto(shopMapper.toDtoList(shopSearchDao.findShopList(name)),
                itemMapper.toDtoList(itemSearchDao.findItemList(name)));
    }
}
