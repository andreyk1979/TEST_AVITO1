package com.amr.project.webapp.controller;

import com.amr.project.converter.BasketMapper;
import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.BasketService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.service.impl.UserServiceImp;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    private final OrderMapper orderMapper;
    private final UserServiceImp userServiceImp;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse> payService;
    private final BasketMapper basketMapper;
    private final BasketService basketService;


    @Autowired
    public OrderRestController(OrderMapper orderMapper,
                               UserServiceImp userServiceImp,
                               OrderServiceImpl orderService,
                               PayService<BillResponse> payService, BasketMapper basketMapper, BasketService basketService) {

        this.orderMapper = orderMapper;
        this.userServiceImp = userServiceImp;
        this.orderService = orderService;
        this.payService = payService;
        this.basketMapper = basketMapper;
        this.basketService = basketService;
    }


    @GetMapping("/get/{id}")
    public Order getOneOrder (@PathVariable Long id) {
        return orderService.findById(id);
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
