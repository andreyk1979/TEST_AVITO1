package com.amr.project.facade;

import java.math.BigDecimal;
import java.util.Calendar;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.webapp.controller.OrderRestController;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

@Service
@Transactional
public class OrderRestFacade {

    private final OrderMapper orderMapper;
    private final OrderServiceImpl orderService;
    private final MailService mailService;
    private final PayService<BillResponse, BillPaymentClient> payService;

    public OrderRestFacade(OrderMapper orderMapper,
                           OrderServiceImpl orderService,
                           MailService mailService,
                           PayService<BillResponse,
                                   BillPaymentClient> payService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.mailService = mailService;
        this.payService = payService;
    }

    public OrderDto createOrderFromBasket(User user) {
        OrderDto orderDto = new OrderDto();
        orderService.setPositionCountFromBasket(orderDto, user.getId());
        orderService.lockItemsRests(orderDto);
        Order order = orderService.createOrderFromBasket(orderDto, user);
        return orderMapper.toDto(order);
    }

    public void addDescription(Long orderId, String description) {
        Order order = orderService.findById(orderId);
        order.setDescription(description);
        orderService.update(order);
    }

    public BigDecimal useCoupon(Long couponId, Long orderId) {
        return orderService.useCoupon(orderId, couponId);

    }

    @GetMapping("/get/{id}")
    public Order getOneOrder (Long id) {
        return orderService.findById(id);
    }

    public void waitPay(OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.START) {
            order.setStatus(Status.WAITING);
            orderService.update(order);
        }
    }

    public void getPay() {

    }

    public void sentOrder(OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.PAID) {
            order.setStatus(Status.SENT);
            orderService.update(order);
        }
    }

    public void deliverOrder(OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        if (order.getStatus() == Status.SENT) {
            order.setStatus(Status.DELIVERED);
            orderService.update(order);
        }
    }

    public OrderDto getOrderById(Long id) {
        return orderMapper.toDto(orderService.findById(id));
    }

    public void checkStatusOrder() {
        Calendar calendar = Calendar.getInstance();
        orderService.findAll().forEach(order -> {
            if (order.getExpectedDeliveryDate().getTime().before(calendar.getTime())) {
                order.setStatus(Status.DELIVERED);
                orderService.update(order);
            }
        });
    }

    public void deleteNotActualOrders() {
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
