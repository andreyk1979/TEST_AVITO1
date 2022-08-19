package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Bill;

public interface BillDao extends  ReadWriteDao<Bill,Long> {
    Bill findByEmail(String email);
    void saveBill(Bill bill);

}
