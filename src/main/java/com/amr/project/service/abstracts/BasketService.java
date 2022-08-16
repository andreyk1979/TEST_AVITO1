package com.amr.project.service.abstracts;


import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.ItemCountPosition;

import java.util.List;

public interface BasketService extends ReadWriteService<Basket, Long> {
    void clear(Long id);
    void updateItemsCounts(Long id, List<ItemCountPosition> itemCountPositions);
    void changeOneItemCount(Long id, ItemCountPosition position);
    int decreaseOrDeleteOneItemCount(Long id, ItemCountPosition position);
    void deleteOneItemCount(Long id, Item item);
}
