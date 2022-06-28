package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.DiscountDao;
import com.amr.project.model.entity.Discount;
import com.amr.project.service.abstracts.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl extends ReadWriteServiceImpl<Discount, Long> implements DiscountService {
    private DiscountDao discountDao;

    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao) {
        super(discountDao);

    }
}
