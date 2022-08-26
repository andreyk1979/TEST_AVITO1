package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Bill;

public interface BillService extends ReadWriteService<Bill,Long>{

    Bill findByEmail(String email);
    void saveBill(Bill bill);
}
