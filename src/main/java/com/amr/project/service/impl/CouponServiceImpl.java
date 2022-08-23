package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.entity.Coupon;
import com.amr.project.service.abstracts.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponServiceImpl extends ReadWriteServiceImpl<Coupon, Long> implements CouponService {
    private CouponDao couponDao;

    @Autowired
    public CouponServiceImpl(CouponDao couponDao) {
        super(couponDao);
    }

    @Override
    public Optional<Integer> useCoupon(Long couponId) {
        Coupon coupon = super.findById(couponId);
        if (coupon.isUsed()) return Optional.empty();
        coupon.setUsed(true);
        super.persist(coupon);
        return Optional.of(coupon.getDiscount());
    }
}
