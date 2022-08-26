package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;
import java.util.List;

public interface ItemService extends ReadWriteService<Item, Long> {

    List<Item> getTwoMostPopularItemForShop(Long shopId);

    List<Item> getItemsToBeModerated();

    void pretendToDelete(Long id);

    List<Item> getItemForShop (Long shopId);

}
