package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.impl.DaoImplShop;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceImplShop implements ReadWriteService<Shop, Long> {

    private final DaoImplShop dao;

    public ServiceImplShop(DaoImplShop dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public Shop persist(Shop entity) {
        dao.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void update(Shop entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(Shop entity) {
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
    public Shop findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> findAll() {
        return dao.findAll();
    }
}
