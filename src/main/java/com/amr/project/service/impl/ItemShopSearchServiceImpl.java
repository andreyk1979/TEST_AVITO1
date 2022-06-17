package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.service.abstracts.ItemShopSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemShopSearchServiceImpl implements ItemShopSearchService {

    protected final ItemDao itemSearchDao;
    protected final ShopDao shopSearchDao;
    protected final ItemMapper itemMapper;
    protected final ShopMapper shopMapper;

    public ItemShopSearchServiceImpl(ItemDao itemSearchDao, ShopDao shopSearchDao, ItemMapper itemMapper, ShopMapper shopMapper) {
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

    @Override
    @Transactional
    public ItemShopDto getItemShopDto(String name, Integer page, Integer size) {
        return new ItemShopDto(shopMapper.toDtoList(shopSearchDao.findShopList(name, page, size)),
                itemMapper.toDtoList(itemSearchDao.findItemList(name, page, size)));
    }

    @Override
    @Transactional
    public List<Long> getCountItemShop(String name) {
        List<Long> countItemShop = new ArrayList<>();
        countItemShop.add(itemSearchDao.getCountItem(name));
        countItemShop.add(shopSearchDao.getCountShop(name));
        return countItemShop;
    }
}
