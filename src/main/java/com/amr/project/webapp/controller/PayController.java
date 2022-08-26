package com.amr.project.webapp.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.amr.project.facade.PayFacade;


@Controller
@RequestMapping("/pay/order")
public class PayController {
    private final PayFacade payFacade;

    public PayController(PayFacade payFacade) {
        this.payFacade = payFacade;
    }

    @PostMapping()
    public String doPay (@RequestParam Long orderId) {
        return payFacade.doPay(orderId);
    }

    @ResponseBody
    @GetMapping("/cancel}")
    public String doCancelBill(@RequestParam String userEmail) {
        return payFacade.doCancelBill(userEmail);
    }
    @ResponseBody
    @GetMapping("/status")
    public String getBillStatus(@RequestParam String userEmail) {
        return payFacade.getBillStatus(userEmail);
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkBillStatus() {
        payFacade.checkBillStatus();
    }
}
