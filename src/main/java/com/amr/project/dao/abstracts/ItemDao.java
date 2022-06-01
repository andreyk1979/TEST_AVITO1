package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ItemDao<Item, Long> extends ReadWriteDao<Item, Long> {

    List<Item> findItemList(String name);
}
