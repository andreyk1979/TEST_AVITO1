package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ItemRepository;
import com.amr.project.dao.abstracts.ShopRepository;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainPageServiceImpl implements MainPageService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final ItemMapper itemMapper;
    private final ShopMapper shopMapper;


    @Autowired
    public MainPageServiceImpl(ItemRepository itemRepository, ShopRepository shopRepository, ItemMapper itemMapper, ShopMapper shopMapper) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
    }

    @Transactional
    public List<ItemDto> showFourMostPopularItems() {
        return itemMapper.toDtoList(itemRepository.findTop4ByOrderByRatingDesc());
    }

    @Transactional
    public List<ShopDto> showSixMostPopularShops() {
        return shopMapper.toDtoList(shopRepository.findTop6ByOrderByRatingDesc());
    }


}
