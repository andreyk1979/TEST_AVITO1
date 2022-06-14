package com.amr.project.webapp.controller;

import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.service.impl.UserServiceImp;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderMapper orderMapper;
    private final UserServiceImp userServiceImp;
    private final OrderServiceImpl orderService;

    public OrderController(OrderMapper orderMapper,
                           UserServiceImp userServiceImp,
                           OrderServiceImpl orderService) {

        this.orderMapper = orderMapper;
        this.userServiceImp = userServiceImp;
        this.orderService = orderService;
    }

    @PutMapping
    public void createOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderMapper.toModel(orderDto);
        User user = userServiceImp.findById(orderDto.getUserId());
        order.setUser(user);
        order.setStatus(Status.START);
        Calendar deliveryDate = Calendar.getInstance();
        deliveryDate.setTime(Date.from(orderDto.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        order.setExpectedDeliveryDate(deliveryDate);
        orderService.update(order);
    }

    @PostMapping("/paying")
    public void waitPay(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.START) {
            order.setStatus(Status.WAITING);
            orderService.update(order);
        }
    }

    //TODO сделать метод, который проверяет оплату и меняет статус. Зависит от метода оплаты
    @PostMapping("/pay")
    public void getPay() {
    }

    @PostMapping("/sent")
    public void sentOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.PAID) {
            order.setStatus(Status.SENT);
            orderService.update(order);
        }
    }

    @PostMapping("/deliver")
    public void deliverOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.SENT) {
            order.setStatus(Status.DELIVERED);
            orderService.update(order);
        }

    }

    @Scheduled(cron = "0 0 * * * *")
    public void checkStatusOrder() {
        Calendar calendar = Calendar.getInstance();
        orderService.findAll().forEach(order -> {
            if (order.getExpectedDeliveryDate().getTime().before(calendar.getTime())) {
                order.setStatus(Status.DELIVERED);
                orderService.update(order);
            }
        });

    }
}
