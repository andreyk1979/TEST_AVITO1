package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryRepository;
import com.amr.project.dao.abstracts.ItemRepository;
import com.amr.project.dao.abstracts.ShopRepository;
import com.amr.project.model.dto.MainPageDto;
import com.amr.project.service.abstracts.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MainPageServiceImpl implements MainPageService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final ItemMapper itemMapper;
    private final ShopMapper shopMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MainPageServiceImpl(ItemRepository itemRepository, ShopRepository shopRepository, ItemMapper itemMapper,
                               ShopMapper shopMapper, CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public MainPageDto getMainPageDto() {

        return MainPageDto.builder()
                .categoryDto(categoryMapper.toDtoList(categoryRepository.findAll()))
                .itemDtoList(itemMapper.toDtoList(itemRepository.findTop4ByOrderByRatingDesc()))
                .shopDtoList(shopMapper.toDtoList(shopRepository.findTop6ByOrderByRatingDesc()))
                .build();
    }
}
