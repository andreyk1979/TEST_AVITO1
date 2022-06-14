package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends  ReadWriteDao<Order,Long>{
}
