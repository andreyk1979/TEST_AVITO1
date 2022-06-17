package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.entity.report.SalesHistory;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public class SalesHistoryDaoImpl extends ReadWriteDaoImpl<SalesHistory, Long> implements SalesHistoryDao {
    @Override
    public List<SalesHistory> findSalesHistoryFromOrderByShopId(Long id) {
        return em.createQuery("select history from Order o join o.itemsInOrder as items join items.history as history where items.shop.id=:id and o.status=3  order by history.orderDate DESC ", SalesHistory.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<SalesHistory> findSalesByDate(Long id, Calendar date1, Calendar date2) {
        return em.createQuery("select history from Order o join o.itemsInOrder as items join items.history as history where history.orderDate >= :date1 and history.orderDate <= :date2 and items.shop.id=:id and o.status=3 order by history.orderDate DESC", SalesHistory.class)
                .setParameter("id", id)
                .setParameter("date1", date1)
                .setParameter("date2", date2)
                .getResultList();
    }

    @Override
    public List<SalesHistory> findSalesByItemName(Long id, String itemName) {
        return em.createQuery("select history from Order o join o.itemsInOrder as items join items.history as history where items.name=:name and items.shop.id=:id and o.status=3 order by items.name ASC ", SalesHistory.class)
                .setParameter("id", id)
                .setParameter("name", itemName)
                .getResultList();
    }
}
