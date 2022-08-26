package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.*;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.*;
import com.amr.project.util.validation.basket.OnAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;


@Service
@Validated
public class OrderServiceImpl extends ReadWriteServiceImpl<Order, Long> implements OrderService {
    final private OrderDao orderDao;
    final private BasketService basketService;
    final private ItemService itemService;
    final private CouponService couponService;
    @Autowired
    public OrderServiceImpl(@Qualifier("orderDaoImpl") ReadWriteDao<Order, Long> dao,
                            BasketService basketService,
                            ItemService itemService,
                            CouponService couponService,
                            OrderDao orderDao) {
        super(dao);
        this.basketService = basketService;
        this.itemService = itemService;
        this.couponService = couponService;
        this.orderDao = orderDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAllNotActual() {
        return orderDao.findAllNotActual();
    }


    @Override
    @Validated(OnAdd.class)
    @Transactional
    public void lockItemsRests(@Valid OrderDto orderDto) {
        orderDto.getPositionCount().forEach((itemId, count) -> {
            Item item = itemService.findById(itemId);
            item.setCount(item.getCount() - count);
            itemService.update(item);
        });
    }

    @Override
    @Transactional
    public void unlockItemsRests(OrderDto orderDto) {
        orderDto.getPositionCount().forEach((itemId, count) -> {
            Item item = itemService.findById(itemId);
            item.setCount(item.getCount() + count);
            itemService.update(item);
        });

    }

    @Override
    @Transactional
    public Order createOrderFromBasket(OrderDto orderDto, User user) {
        try {
            Calendar expectedDeliveryDate;
            expectedDeliveryDate = new GregorianCalendar();
            expectedDeliveryDate.roll(Calendar.DAY_OF_MONTH, 3);
            List<Item> items = new ArrayList<>();

            BigDecimal total = orderDto.getPositionCount().entrySet().stream()
                    .reduce(BigDecimal.ZERO, (acc, entity) -> {
                        Item item = itemService.findById(entity.getKey());
                        items.add(item); // mutate items out of reduce in order not to call BD again
                        BigDecimal priceWithDiscount = item.getPrice()
                                .subtract(item.getPrice()
                                        .multiply(BigDecimal.valueOf(item.getDiscount() / 100.0)));
                        BigDecimal sumPrice = priceWithDiscount.multiply(BigDecimal.valueOf(entity.getValue()));
                        return acc.add(sumPrice);
                    }, BigDecimal::add);

            Order order = Order.builder()
                    .orderDate(Calendar.getInstance())
                    .expectedDeliveryDate(expectedDeliveryDate)
                    .grandTotal(total)
                    .currency("RUB")
                    .status(Status.START)
                    .user(user)
                    .itemsInOrder(items)
                    .positionCount(orderDto.getPositionCount())
                    .address(user.getAddress())
                    .build();
            order.setQiwiId(UUID.randomUUID().toString());
            super.persist(order);

            //basketService.clear(user.getId()); // todo - makeev clean basket here if it's need

            return order;
        } catch (Exception ex){
            unlockItemsRests(orderDto);
            throw ex;
        }
    }

    @Override
    public void setPositionCountFromBasket(OrderDto orderDto, Long userId) {
        Basket basket =  basketService.findById(userId);
        if (orderDto.getPositionCount() == null) {
            orderDto.setPositionCount(new HashMap<>());
            basket.getItemsCount().forEach((item, count) -> {
                orderDto.getPositionCount().put(item.getId(), count);
            });
        }
    }

    @Override
    @Transactional
    public BigDecimal useCoupon(Long orderId, Long couponId) {
        Order order = super.findById(orderId);
        Coupon coupon = couponService.findById(couponId);
        if(coupon.isUsed()) return order.getGrandTotal();
        BigDecimal totalPriceWithCoupon = order.getGrandTotal()
                .subtract(order.getGrandTotal()
                        .multiply(BigDecimal.valueOf(coupon.getDiscount() / 100.0)));
        order.setGrandTotal(totalPriceWithCoupon);
        couponService.useCoupon(couponId);
        super.update(order);
        return totalPriceWithCoupon;

    }

}
