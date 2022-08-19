package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.BillDao;
import com.amr.project.model.entity.Bill;
import org.springframework.stereotype.Repository;

@Repository
public class BillDaoImpl extends ReadWriteDaoImpl<Bill, Long> implements BillDao {

}
