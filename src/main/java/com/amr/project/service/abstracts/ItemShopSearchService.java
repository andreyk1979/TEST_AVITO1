package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemShopDto;

import java.util.List;

public interface ItemShopSearchService {

    ItemShopDto getItemShopDto(String name);

    ItemShopDto getItemShopDto(String name, Integer page, Integer size);

    List<Long> getCountItemShop(String name);
}
