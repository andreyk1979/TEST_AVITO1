package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DaoImplItem implements ReadWriteDao<Item, Long> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void persist(Item entity) {
        em.persist(entity);
    }

    @Override
    public void update(Item entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Item entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void deleteByIdCascadeEnable(Long id) {
        em.remove(em.find(Item.class,id));
    }

    @Override
    public void deleteByIdCascadeIgnore(Long id) {
        em.createQuery("DELETE FROM " + Item.class.getName() + " u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existsById(Long id) {
        return em.find(Item.class, id) != null;
    }

    @Override
    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select u from " + Item.class.getName() + " u", Item.class)
                .getResultList();
    }
}
