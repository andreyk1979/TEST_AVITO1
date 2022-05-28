package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DaoImplShop implements ReadWriteDao<Shop, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Shop entity) {
        em.persist(entity);
    }

    @Override
    public void update(Shop entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Shop entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void deleteByIdCascadeEnable(Long id) {
        em.remove(em.find(Shop.class,id));
    }

    @Override
    public void deleteByIdCascadeIgnore(Long id) {
        em.createQuery("DELETE FROM " + Shop.class.getName() + " u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existsById(Long id) {
        return em.find(Shop.class, id) != null;
    }

    @Override
    public Shop findById(Long id) {
        return em.find(Shop.class, id);
    }

    @Override
    public List<Shop> findAll() {
        return em.createQuery("select u from " + Shop.class.getName() + " u", Shop.class)
                .getResultList();
    }
}
