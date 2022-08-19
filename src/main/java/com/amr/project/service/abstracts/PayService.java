package com.amr.project.service.abstracts;

import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Bill;

public interface PayService <T> extends ReadWriteService<Bill,Long>{

    // определяем общий метод соединения с любым АПИ для оплаты
    T connectWithMerchant(OrderDto orderDto);
    Bill createBill(T response);
    void saveBill(Bill bill);

}
