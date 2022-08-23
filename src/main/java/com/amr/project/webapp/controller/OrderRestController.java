package com.amr.project.webapp.controller;

import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Calendar;
import com.amr.project.service.abstracts.PayService;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    private final OrderMapper orderMapper;
    private final OrderServiceImpl orderService;
    private final MailService mailService;
    private final PayService<BillResponse, BillPaymentClient> payService;

    @Autowired
    public OrderRestController(OrderMapper orderMapper,
                               OrderServiceImpl orderService,
                               MailService mailService,
                               PayService<BillResponse, BillPaymentClient> payService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.mailService = mailService;
        this.payService = payService;
    }
    @ApiOperation(value = "Метод createOrderFromBasket",
            notes = "строит заказ по имеющейся корзине")
    @PutMapping
    public OrderDto createOrderFromBasket(@AuthenticationPrincipal User user) {
        OrderDto orderDto = new OrderDto();
        orderService.setPositionCountFromBasket(orderDto, user.getId());
        orderService.lockItemsRests(orderDto);
        Order order = orderService.createOrderFromBasket(orderDto, user);
        return orderMapper.toDto(order);
    }

    @ApiOperation(value = "Метод addDescription",
            notes = "добавляет комментарий от юзера к заказу")
    @PutMapping("/description/{orderId}")
    public void addDescription(@PathVariable Long orderId, @RequestBody String description) {
        Order order = orderService.findById(orderId);
        order.setDescription(description);
        orderService.update(order);
    }

    @ApiOperation(value = "Метод useCoupon",
            notes = "применяет купон к заказу, меняет и возвращает итоговую цену заказа и гасит купон")
    @PutMapping("/coupon/{couponId}/{orderId}")
    public BigDecimal useCoupon(@PathVariable Long couponId, @PathVariable Long orderId) {
        return orderService.useCoupon(orderId, couponId);

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

    @ApiOperation(value = "Метод getOrderById",
            notes = "возвращает OrderDto по id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderMapper.toDto(orderService.findById(id));
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

    @Scheduled(cron = "@hourly")
    void deleteNotActualOrders() {
        org.slf4j.Logger logger = LoggerFactory.getLogger(OrderRestController.class);
        orderService.findAllNotActual().forEach(order -> {
            String name;
            String email;
            try {
                name = order.getUser().getUsername();
                email = order.getUser().getEmail();
            } catch (NullPointerException ex) {
                logger.warn("Deleted Order with id: {}, had User == null", order.getId());
                orderService.unlockItemsRests(orderMapper.toDto(order));
                orderService.delete(order);
                return;
            }
            String message = String.format("Уважамый ,%s! \n" +
                            "Ваш заказ № %s на сайте Avito, был удален в связи \n" +
                            "с отсутствием оплаты в течение %s часов \n" +
                            "держитесь там и хорошего вам настроения!",
                    name, order.getId(), Order.EXPIRATION_HOURS);
            mailService.send(email, "Order delete in Avito 2.0", message);
            logger.info("Order id: {}, was deleted by schedule - not paid", order.getId());
            orderService.unlockItemsRests(orderMapper.toDto(order));
            orderService.delete(order);
        });
    }
}
