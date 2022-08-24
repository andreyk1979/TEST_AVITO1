package com.amr.project.webapp.controller;

import java.math.BigDecimal;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.OrderRestFacade;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    private final OrderRestFacade orderRestFacade;

    public OrderRestController(OrderRestFacade orderRestFacade) {
        this.orderRestFacade = orderRestFacade;
    }

    @ApiOperation(value = "Метод createOrderFromBasket",
            notes = "строит заказ по имеющейся корзине")
    @PutMapping
    public OrderDto createOrderFromBasket(@AuthenticationPrincipal User user) {
        return orderRestFacade.createOrderFromBasket(user);
    }

    @ApiOperation(value = "Метод addDescription",
            notes = "добавляет комментарий от юзера к заказу")
    @PutMapping("/description/{orderId}")
    public void addDescription(@PathVariable Long orderId, @RequestBody String description) {
        orderRestFacade.addDescription(orderId, description);
    }

    @ApiOperation(value = "Метод useCoupon",
            notes = "применяет купон к заказу, меняет и возвращает итоговую цену заказа и гасит купон")
    @PutMapping("/coupon/{couponId}/{orderId}")
    public BigDecimal useCoupon(@PathVariable Long couponId, @PathVariable Long orderId) {
        return orderRestFacade.useCoupon(couponId, orderId);

    }

    @GetMapping("/get/{id}")
    public Order getOneOrder (@PathVariable Long id) {
        return orderRestFacade.getOneOrder(id);
    }

    @PostMapping("/paying")
    public void waitPay(@Valid @RequestBody OrderDto orderDto) {
        orderRestFacade.waitPay(orderDto);
    }

    //TODO сделать метод, который проверяет оплату и меняет статус. Зависит от метода оплаты
    @PostMapping("/pay")
    public void getPay() {
        orderRestFacade.getPay();
    }

    @PostMapping("/sent")
    public void sentOrder(@Valid @RequestBody OrderDto orderDto) {
        orderRestFacade.sentOrder(orderDto);
    }

    @PostMapping("/deliver")
    public void deliverOrder(@Valid @RequestBody OrderDto orderDto) {
        orderRestFacade.deliverOrder(orderDto);
    }

    @ApiOperation(value = "Метод getOrderById",
            notes = "возвращает OrderDto по id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderRestFacade.getOrderById(id);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void checkStatusOrder() {
        orderRestFacade.checkStatusOrder();
    }

    @Scheduled(cron = "@hourly")
    void deleteNotActualOrders() {
        orderRestFacade.deleteNotActualOrders();
    }
}
