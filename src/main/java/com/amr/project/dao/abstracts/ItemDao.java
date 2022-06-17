package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long> {
    List<Item> getTwoMostPopularItemForShop (Long shopId);

    List<Item> findItemList(String name);

    List<Item> findItemList(String name, Integer page, Integer size);

    List<Item> getFourMostPopularItem();

    void isPretendedToBeDeleted(Long id);

    List<Item> getItemForShop (Long shopId);

    Long getCountItem(String name);

    List<Item> getItemsToBeModerated();


}
