package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemRepository;
import com.amr.project.model.entity.Item;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ItemRepositoryImpl extends ReadWriteDaoImpl<Item, Long> implements ItemRepository<Item, Long> {


    public List<Item> getFourMostPopularItem() {

        return em.createQuery("select s from Item s order by s.rating DESC")
                .setMaxResults(4).getResultList();
    }
}
