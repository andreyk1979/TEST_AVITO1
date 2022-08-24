package com.amr.project.webapp.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.OrderFacade;
import com.amr.project.model.entity.User;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping
    public String createOrder(@AuthenticationPrincipal User user, Model model) {
        return orderFacade.createOrder(user, model);
    }

    @Scheduled(cron = "@hourly")
    void deleteNotActualOrders() {
        orderFacade.deleteNotActualOrders();
    }


}
