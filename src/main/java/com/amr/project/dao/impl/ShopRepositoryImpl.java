package com.amr.project.dao.impl;


import com.amr.project.dao.abstracts.ShopRepository;

import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopRepositoryImpl extends ReadWriteDaoImpl<Shop, Long> implements ShopRepository<Shop, Long> {

    @Override
    public List<Shop> getSixMostPopularShop() {
        return em.createQuery("select s from Shop s order by s.rating DESC")
                .setMaxResults(6).getResultList();
    }
}
