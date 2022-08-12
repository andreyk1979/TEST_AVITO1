package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;
import java.util.List;

public interface ItemService extends ReadWriteService<Item, Long> {

    void pretendToDelete(Long id);

    List<Item> getItemForShop (Long shopId);

}
