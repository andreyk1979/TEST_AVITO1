package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Coupon;

import java.util.Optional;

public interface CouponService extends ReadWriteService<Coupon, Long> {
    Optional<Integer> useCoupon(Long couponId);
}
