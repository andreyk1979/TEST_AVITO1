package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.BillDao;
import com.amr.project.model.entity.Bill;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class BillDaoImpl extends ReadWriteDaoImpl<Bill, Long> implements BillDao {

    public BillDaoImpl() {
        super();
    }

    @Override
    public Bill findByEmail(String email) {
        return (Bill) em.createQuery("select b from Bill b where b.customerEmail=:email")
                .setParameter("email", email).getSingleResult();


    }

    @Override
    public void saveBill(Bill bill) {
        em.persist(bill);
    }
}
