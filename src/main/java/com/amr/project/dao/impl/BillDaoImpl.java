package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.BillDao;
import com.amr.project.model.entity.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class BillDaoImpl extends ReadWriteDaoImpl<Bill, Long> implements BillDao {

    public BillDaoImpl() {
        super();
    }

    @Override
    public Bill findByEmail(String email) {
        Query query = em.createQuery("select Bill from Bill b where b.customerEmail=:email")
                .setParameter("email", email);
        Bill bill = (Bill) query.getSingleResult();
        return bill;

    }

    @Override
    public void saveBill(Bill bill) {
        em.persist(bill);
    }
}
