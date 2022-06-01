package com.amr.project.dao.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.dto.ItemDto;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<ItemDto, Long> implements ItemDao<ItemDto, Long> {

    protected final ItemMapper itemMapper;

    public ItemDaoImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public List<ItemDto> findItemList(String name) {
        Query query = em.createQuery("from Item where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return itemMapper.toDtoItem(query.getResultList());
    }
}
