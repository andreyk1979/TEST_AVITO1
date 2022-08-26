package com.amr.project.service.abstracts;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;

public interface PayService <T, K> extends ReadWriteService<Bill,Long>{

    // определяем общий метод соединения с любым АПИ для оплаты
    K getClient ();
    T connectWithMerchant(OrderDto orderDto);
    Bill createBill(T response);
    String getBillStatus(String billId);
}
