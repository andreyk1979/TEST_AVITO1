package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemRepository;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl extends ReadWriteServiceImpl<Item, Long> implements ItemService {

    protected final ItemRepository<Item, Long> itemRepository;

    @Autowired
    public ItemServiceImpl(ReadWriteDao<Item, Long> dao, ItemRepository<Item, Long> itemRepository) {
        super(dao);
        this.itemRepository = itemRepository;
    }

    @Override
    public void isPretendedToBeDeleted(Long id) {

        itemRepository.isPretendedToBeDeleted(id);
    }
}
