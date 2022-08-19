package com.amr.project.webapp.controller;

import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.service.impl.UserServiceImp;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Controller
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

//    @PostMapping
//    public String doPay (@RequestBody OrderDto orderDto) {
//        return "redirect:" + payService.connectWithMerchant(orderDto).getPayUrl();
//    }

    @GetMapping()
    public String doPayTest () {
        OrderDto orderDto = orderMapper.toDto(orderService.findById(1L));
        BillResponse response = payService.connectWithMerchant(orderDto);
        Bill bill = payService.createBill(response);
        payService.saveBill(bill);
        String successURL = response.getPayUrl();
        return "redirect:" + successURL;
    }












// !!!!!!! Методы для проверки состояния счета.

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! МЕТОДЫ НЕДОДЕЛАНЫ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//    @PostMapping("/paying")
//    public void waitPay(@Valid @RequestBody OrderDto orderDto) {
//        Order order = orderService.findById(orderDto.getId());
//        if (order.getStatus() == Status.START) {
//            order.setStatus(Status.WAITING);
//            orderService.update(order);
//        }
//    }
//
//    @PostMapping("/pay")
//    public void getPay() {
//
//    }
//
//    @PostMapping("/sent")
//    public void sentOrder(@Valid @RequestBody OrderDto orderDto) {
//        Order order = orderService.findById(orderDto.getId());
//        if (order.getStatus() == Status.PAID) {
//            order.setStatus(Status.SENT);
//            orderService.update(order);
//        }
//    }
//
//    @PostMapping("/deliver")
//    public void deliverOrder(@Valid @RequestBody OrderDto orderDto) {
//        Order order = orderService.findById(orderDto.getId());
//        if (order.getStatus() == Status.SENT) {
//            order.setStatus(Status.DELIVERED);
//            orderService.update(order);
//        }
//
//    }
//
//    @Scheduled(cron = "0 0 * * * *")
//    public void checkStatusOrder() {
//        Calendar calendar = Calendar.getInstance();
//        orderService.findAll().forEach(order -> {
//            if (order.getExpectedDeliveryDate().getTime().before(calendar.getTime())) {
//                order.setStatus(Status.DELIVERED);
//                orderService.update(order);
//            }
//        });
//    }

}
