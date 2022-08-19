package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends  ReadWriteDao<Order,Long>{
    List<Order> findAllNotActual();
}
