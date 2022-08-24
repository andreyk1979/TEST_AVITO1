package com.amr.project.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;
import com.amr.project.service.abstracts.BillService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

@Service
@Transactional
public class PayFacade {

    private final OrderMapper orderMapper;
    private final BillService billService;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse, BillPaymentClient> payService;

    public PayFacade(OrderMapper orderMapper, BillService billService, OrderServiceImpl orderService, PayService<BillResponse, BillPaymentClient> payService) {
        this.orderMapper = orderMapper;
        this.billService = billService;
        this.orderService = orderService;
        this.payService = payService;
    }

    public String doPay (Long orderId) {
        OrderDto orderDto = orderMapper.toDto(orderService.findById(orderId));
        BillResponse response = payService.connectWithMerchant(orderDto);
        Bill bill = payService.createBill(response);
        bill.setStatus("WAITING");
        billService.saveBill(bill);
        String successURL = response.getPayUrl();
        return "redirect:" + successURL;
    }

    public String doCancelBill(String userEmail) {
        Bill bill = billService.findByEmail(userEmail);
        BillPaymentClient client = payService.getClient();
        BillResponse response = client.cancelBill(bill.getBillId());
        billService.delete(bill);
        return response.getStatus().getValue().getValue();
    }

    public String getBillStatus(String userEmail) {
        Bill bill = billService.findByEmail(userEmail);
        String billStatus = payService.getBillStatus(bill.getBillId());
        bill.setStatus(billStatus);
        billService.update(bill);
        return billStatus;
    }

    public void checkBillStatus() {
        billService.findAll().forEach(bill -> {
            String billStatus = payService.getBillStatus(bill.getBillId());
            bill.setStatus(billStatus);
            billService.update(bill);
        });
    }
}
