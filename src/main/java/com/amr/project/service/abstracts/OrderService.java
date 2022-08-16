package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends ReadWriteService<Order, Long>{
    public Order findAllNotActual();
}
