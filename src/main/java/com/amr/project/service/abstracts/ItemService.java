package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;

public interface ItemService extends ReadWriteService<Item, Long> {

    void isPretendedToBeDeleted(Long id);


}
