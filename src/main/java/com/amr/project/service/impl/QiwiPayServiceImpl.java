package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.BillDao;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.service.abstracts.PayService;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;
@Service
public class QiwiPayServiceImpl extends ReadWriteServiceImpl<Bill,Long> implements PayService<BillResponse> {

    private final static String SECRET_KEY =
            "eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6ImE5YW94cC0wMCIsInVzZXJfaWQiOiI3OTA5O" +
                    "DMyODkwMSIsInNlY3JldCI6IjhiYTkxMjU3NjMwMjkyZmEzNWRjMjA1ZWRiMmY3MTdiODgwMDVmMGQ0MTAxYmE1MmI2MDUyMzc5NWRkYmJhZTYifX0=";

    private final OrderService orderService;

    @Autowired
    public QiwiPayServiceImpl(BillDao dao, OrderService orderService) {
        super(dao);
        this.orderService = orderService;
    }
    public BillPaymentClient getClient (){
        return BillPaymentClientFactory.createDefault(SECRET_KEY);
    }

    // описываем конкретную логику работы с конкретным АПИ (из документации)
    // в ответе получаем объект, содержащий нужную нам ссылку для оплаты
    //@SneakyThrows
    @SneakyThrows
    @Override
    public BillResponse connectWithMerchant(OrderDto orderDto) {
        Order order = orderService.findById(orderDto.getId());
        User customer = order.getUser();

        BillPaymentClient client = getClient();
        CreateBillInfo billInfo = new CreateBillInfo(
                order.getQiwiId(),
                new MoneyAmount(
                        order.getGrandTotal(),
                        Currency.getInstance(order.getCurrency())
                ),
                "comment",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        customer.getEmail(),
                        UUID.randomUUID().toString(),
                        customer.getPhone()
                ),
                "http://localhost:8888/user/" + customer.getId()
        );

        return client.createBill(billInfo);
    }

    public Bill createBill(BillResponse response) {
        return Bill.builder()
                .billId(response.getBillId())
                .amountValue(response.getAmount().getValue())
                .customerEmail(response.getCustomer().getEmail())
                .customerPhone(response.getCustomer().getPhone())
                .expirationDateTime(response.getExpirationDateTime())
                .comment(response.getComment())
                .build();
    }
    @Transactional
    public void saveBill(Bill bill) {
        dao.persist(bill);
    }

}
