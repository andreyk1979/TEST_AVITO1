package com.amr.project.webapp.controller;

import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.service.impl.UserServiceImp;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/pay/order")
public class PayController {
    private final OrderMapper orderMapper;
    private final UserServiceImp userServiceImp;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse> payService;

    @Autowired
    public PayController(OrderMapper orderMapper,
                         UserServiceImp userServiceImp,
                         OrderServiceImpl orderService,
                         PayService<BillResponse> payService) {

        this.orderMapper = orderMapper;
        this.userServiceImp = userServiceImp;
        this.orderService = orderService;
        this.payService = payService;
    }

    @PostMapping()
    public BillResponse doPay (@RequestBody OrderDto orderDto) {
        return payService.connectWithMerchant(orderDto);
    }
}
