package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.impl.CategoryRepositoryImpl;
import com.amr.project.dao.impl.ItemRepositoryImpl;
import com.amr.project.dao.impl.ShopRepositoryImpl;
import com.amr.project.model.dto.MainPageDto;
import com.amr.project.service.abstracts.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MainPageServiceImpl implements MainPageService {
    private final CategoryRepositoryImpl categoryRepository;
    private final ItemRepositoryImpl itemRepository;
    private final ShopRepositoryImpl shopRepository;

    private final ItemMapper itemMapper;
    private final ShopMapper shopMapper;
    private final CategoryMapper categoryMapper;


    @Autowired
    public MainPageServiceImpl(CategoryRepositoryImpl categoryRepository, ItemMapper itemMapper,
                               ShopMapper shopMapper, CategoryMapper categoryMapper,
                               ItemRepositoryImpl itemRepository, ShopRepositoryImpl shopRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
        this.categoryMapper = categoryMapper;

    }

    @Transactional
    @Override
    public MainPageDto getMainPageDto() {

        return MainPageDto.builder()
                .categoryDto(categoryMapper.toDtoList(categoryRepository.findAll()))
                .itemDtoList(itemMapper.toDtoList(itemRepository.getFourMostPopularItem()))
                .shopDtoList(shopMapper.toDtoList(shopRepository.getSixMostPopularShop()))
                .username(null)
                .build();
    }
}
