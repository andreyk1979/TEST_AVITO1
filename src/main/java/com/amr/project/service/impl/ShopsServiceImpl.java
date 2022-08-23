package com.amr.project.service.impl;


import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ShopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopsServiceImpl implements ShopsService {

    private final ShopDao shopRepository;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopsServiceImpl (ShopDao shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Override
    public List<ShopDto> getAllShops () {
        return shopMapper.toDtoList(shopRepository.getAllShops());
    }
}