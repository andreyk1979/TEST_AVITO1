package com.amr.project.service.impl;

import com.amr.project.dao.impl.DaoImplItem;
import com.amr.project.dao.impl.DaoImplShop;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceImplItem implements ReadWriteService<Item, Long> {

    private final DaoImplItem dao;

    public ServiceImplItem(DaoImplItem dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public Item persist(Item entity) {
        dao.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void update(Item entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(Item entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteByIdCascadeEnable(Long id) {
        dao.deleteByIdCascadeEnable(id);
    }

    @Override
    @Transactional
    public void deleteByIdCascadeIgnore(Long id) {
        dao.deleteByIdCascadeIgnore(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return dao.findAll();
    }
}

