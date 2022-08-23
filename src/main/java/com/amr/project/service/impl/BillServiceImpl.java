package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.AddressDao;
import com.amr.project.dao.abstracts.BillDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Bill;
import com.amr.project.service.abstracts.AddressService;
import com.amr.project.service.abstracts.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillServiceImpl extends ReadWriteServiceImpl<Bill,Long> implements BillService {

    private final BillDao billDao;

    public BillServiceImpl(ReadWriteDao<Bill, Long> dao, BillDao billDao) {
        super(dao);
        this.billDao = billDao;
    }

    @Override
    public Bill findByEmail(String email) {
        return billDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void saveBill(Bill bill) {
        billDao.saveBill(bill);
    }
}
