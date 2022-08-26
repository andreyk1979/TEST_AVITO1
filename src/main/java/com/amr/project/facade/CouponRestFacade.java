package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.CouponMapper;
import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.service.abstracts.CouponService;

@Service
@Transactional
public class CouponRestFacade {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    public CouponRestFacade(CouponService couponService, CouponMapper couponMapper) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    public CouponDto createDiscount(CouponDto couponDto) {
        return couponMapper.toDto(couponService.persist(couponMapper.toModel(couponDto)));
    }

    public List<CouponDto> allCoupons(){
        return couponMapper.tolist(couponService.findAll());
    }

    public void deleteCoupon(Long id) {
        couponService.deleteByIdCascadeEnable(id);
    }

    public void updateCoupon(CouponDto couponDto) {
        Coupon coupon = couponMapper.toModel(couponDto);
        couponService.update(coupon);
    }
}
