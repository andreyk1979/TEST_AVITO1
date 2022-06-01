package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<Item, Long> implements ItemDao<Item, Long> {

    @Override
    public List<Item> findItemList(String name) {
        Query query = em.createQuery("from Item where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }
}
