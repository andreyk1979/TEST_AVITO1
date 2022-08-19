package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Order;
import com.amr.project.service.abstracts.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;


@Service
public class OrderServiceImpl extends ReadWriteServiceImpl<Order, Long> implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(@Qualifier("orderDaoImpl") ReadWriteDao<Order, Long> dao, OrderDao orderDao) {
        super(dao);
        this.orderDao = orderDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAllNotActual() {
        return orderDao.findAllNotActual();
    }
}
