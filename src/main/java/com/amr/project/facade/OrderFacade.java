package com.amr.project.facade;

import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.webapp.controller.OrderController;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

@Service
@Transactional
public class OrderFacade {

    private final OrderMapper orderMapper;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse, BillPaymentClient> payService;
    private final MailService mailService;

    public OrderFacade(OrderMapper orderMapper,
                       OrderServiceImpl orderService,
                       PayService<BillResponse,
                               BillPaymentClient> payService,
                       MailService mailService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.payService = payService;
        this.mailService = mailService;
    }

    public String createOrder(User user, Model model) {

        OrderDto orderDto = new OrderDto();
        orderService.setPositionCountFromBasket(orderDto, user.getId());
        orderService.lockItemsRests(orderDto);
        Order order = orderService.createOrderFromBasket(orderDto, user);

        model.addAttribute("activeUser", user);
        model.addAttribute("newOrder", orderMapper.toDto(order));
        return "/orderPage";
    }

    public void deleteNotActualOrders() {
        org.slf4j.Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);
        orderService.findAllNotActual().forEach(order -> {
            String name;
            String email;
            try {
                name = order.getUser().getUsername();
                email = order.getUser().getEmail();
            } catch (NullPointerException ex) {
                logger.warn("Deleted Order with id: {}, had User == null", order.getId());
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
            orderService.delete(order);
        });
    }
}
