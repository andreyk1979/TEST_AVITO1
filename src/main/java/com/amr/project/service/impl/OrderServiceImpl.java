package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Order;
import com.amr.project.service.abstracts.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ReadWriteServiceImpl<Order, Long> implements OrderService {


    @Autowired
    public OrderServiceImpl(@Qualifier("orderDaoImpl") ReadWriteDao<Order, Long> dao) {
        super(dao);
    }

    @Override
    public Order findAllNotActual() {
        return null;
    }
}
