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
}


