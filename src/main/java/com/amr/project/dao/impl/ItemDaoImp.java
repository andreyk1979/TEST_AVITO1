package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDaoImp extends ReadWriteDaoImpl<Item, Long> implements ItemDao {

    @Override
    public List<Item> findItemList(String name) {
        Query query = em.createQuery("from Item where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }

    public List<Item> getFourMostPopularItem() {
        return em.createQuery("select s from Item s order by s.rating DESC")
                .setMaxResults(4).getResultList();
    }

    @Override
    public void isPretendedToBeDeleted(Long id) {
        em.createQuery("update Item set isPretendedToBeDeleted = :boolParam WHERE Item.id = :id")
                .setParameter("boolParam", true)
                .setParameter("id", id);
    }

    @Override
    public List<Item> getTwoMostPopularItemForShop(Long shopId) {
        Query query = em.createQuery("from Item where shop.id =:param order by rating DESC");
        query.setParameter("param", shopId);
        return query.setMaxResults(2).getResultList();
    }

    @Override
    public List<Item> getItemForShop(Long shopId) {
        Query query = em.createQuery("from Item where shop.id =:param order by rating DESC");
        query.setParameter("param", shopId);
        return query.getResultList();
    }

    @Override
    public List<Item> getItemsToBeModerated() {

        return em.createQuery("from Item where is_moderated = false").getResultList();
    }
}
