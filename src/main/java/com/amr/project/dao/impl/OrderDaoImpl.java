package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public class OrderDaoImpl extends ReadWriteDaoImpl<Order, Long> implements OrderDao {

    @Override
    public List<Order> findAllNotActual() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, - Order.EXPIRATION_HOURS);
        return em.createQuery("select o from Order o where o.orderDate < :time and " +
                        "(o.status = :statusStart or o.status = :statusWait)", Order.class)
                .setParameter("time", calendar)
                .setParameter("statusStart", Status.START)
                .setParameter("statusWait", Status.WAITING)
                .getResultList();
    }
}
