package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ShopDaoImp extends ReadWriteDaoImpl<Shop, Long> implements ShopDao {

    @Override
    public List<Shop> getSixMostPopularShop() {
        return em.createQuery("select s from Shop s order by s.rating DESC")
                .setMaxResults(6).getResultList();
    }

    @Override
    public List<Shop> findShopList(String name) {
        Query query = em.createQuery("from Shop where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Shop> getShopsToBeModerated() {

        Query query = em.createQuery("from Shop where is_moderated = false");
        return query.getResultList();
    }

    @Override
    public List<Shop> findShopList(String name, Integer page, Integer size) {
        Query query = em.createQuery("from Shop where lower(name) like :name order by rating");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        query.setFirstResult(page);
        return query.setMaxResults(size).getResultList();
    }

    @Override
    public Long getCountShop(String name) {
        Query query = em.createQuery("SELECT COUNT(*) as countShop from Shop where (lower(name) like :name)");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        List<Object> list = query.getResultList();

        return (Long) list.get(0);
    }

    @Override
    public List<Shop> getAllShops() {
        return em.createQuery("select s from Shop s order by s.rating DESC").getResultList();
    }
}


