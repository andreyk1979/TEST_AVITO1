package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopSearchDao;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ShopSearchDaoImpl extends ReadWriteDaoImpl<Shop, Long> implements ShopSearchDao<Shop,Long> {

    @Override
    public List<Shop> findShopList(String name) {
        Query query = em.createQuery("from Shop where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }
}
