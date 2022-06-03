package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDaoImp extends ReadWriteDaoImpl<Item, Long> implements ItemDao {

    @Override
    public List<Item> getTwoMostPopularItemForShop(Long shopId) {
        Query query = em.createQuery("from Item where shop.id =:param order by rating DESC");
        query.setParameter("param", shopId);
        return query.setMaxResults(2).getResultList();
    }
}
