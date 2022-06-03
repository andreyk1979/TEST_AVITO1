package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemRepository;

import com.amr.project.model.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class ItemRepositoryImpl extends ReadWriteDaoImpl<Item, Long> implements ItemRepository<Item, Long> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void isPretendedToBeDeleted(Long id) {
        em.createQuery("update Item set isPretendedToBeDeleted = :boolParam WHERE Item.id = :id")
                .setParameter("boolParam", true)
                .setParameter("id",id);

    }
}
