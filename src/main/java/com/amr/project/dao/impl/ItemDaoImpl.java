package com.amr.project.dao.impl;


import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

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
        return (List<ItemDto>) query.getResultList().stream()
                .map(item -> itemMapper.toDto((Item) item))
                .collect(Collectors.toList());
    }
}
