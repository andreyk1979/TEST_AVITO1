package com.amr.project.webapp.controller;

import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;
import com.amr.project.service.abstracts.BillService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/pay/order")
public class PayController {
    private final OrderMapper orderMapper;
    private final BillService billService;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse, BillPaymentClient> payService;
    @Autowired
    public PayController(OrderMapper orderMapper, BillService billService,
                         OrderServiceImpl orderService,
                         PayService<BillResponse, BillPaymentClient> payService) {
        this.orderMapper = orderMapper;
        this.billService = billService;
        this.orderService = orderService;
        this.payService = payService;
    }

    @PostMapping()
    public String doPay (@RequestParam Long orderId) {
        OrderDto orderDto = orderMapper.toDto(orderService.findById(orderId));
        BillResponse response = payService.connectWithMerchant(orderDto);
        Bill bill = payService.createBill(response);
        bill.setStatus("WAITING");
        billService.saveBill(bill);
        String successURL = response.getPayUrl();
        return "redirect:" + successURL;
    }

    @ResponseBody
    @GetMapping("/cancel}")
    public String doCancelBill(@RequestParam String userEmail) {
        Bill bill = billService.findByEmail(userEmail);
        BillPaymentClient client = payService.getClient();
        BillResponse response = client.cancelBill(bill.getBillId());
        billService.delete(bill);
        return response.getStatus().getValue().getValue();
    }
    @ResponseBody
    @GetMapping("/status")
    public String getBillStatus(@RequestParam String userEmail) {
        Bill bill = billService.findByEmail(userEmail);
        String billStatus = payService.getBillStatus(bill.getBillId());
        bill.setStatus(billStatus);
        billService.update(bill);
        return billStatus;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkBillStatus() {
        billService.findAll().forEach(bill -> {
            String billStatus = payService.getBillStatus(bill.getBillId());
            bill.setStatus(billStatus);
            billService.update(bill);
        });
    }
}
