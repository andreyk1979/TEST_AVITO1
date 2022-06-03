package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService extends ReadWriteService<Item, Long> {

    void isPretendedToBeDeleted(Long id);

}
