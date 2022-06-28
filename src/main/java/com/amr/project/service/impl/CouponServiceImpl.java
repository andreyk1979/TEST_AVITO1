package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.entity.Coupon;
import com.amr.project.service.abstracts.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends ReadWriteServiceImpl<Coupon, Long> implements CouponService {
    private CouponDao couponDao;

    @Autowired
    public CouponServiceImpl(CouponDao couponDao) {
        super(couponDao);

    }
}
