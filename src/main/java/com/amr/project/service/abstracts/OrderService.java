package com.amr.project.service.abstracts;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderService extends ReadWriteService<Order, Long>{
    public List<Order> findAllNotActual();
    public void lockItemsRests(@Valid OrderDto orderDto);
    public void unlockItemsRests(OrderDto orderDto);

    public Order createOrderFromBasket(OrderDto orderDto, User user);
    public void setPositionCountFromBasket(OrderDto orderDto, Long userId);
    public BigDecimal useCoupon(Long orderId, Long couponId);
}
