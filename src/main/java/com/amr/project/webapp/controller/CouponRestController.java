package com.amr.project.webapp.controller;

import com.amr.project.converter.CouponMapper;
import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.service.abstracts.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/coupon")
public class CouponRestController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @Autowired
    public CouponRestController (CouponService couponService, CouponMapper couponMapper) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public CouponDto createDiscount(@RequestBody CouponDto couponDto) {
        return couponMapper.toDto(couponService.persist(couponMapper.toModel(couponDto)));
    }

    @GetMapping("/coupon")
    @ResponseStatus(HttpStatus.OK)
    public List<CouponDto> allCoupons(){
        return couponMapper.tolist(couponService.findAll());
    }

    @DeleteMapping("/coupon/{id}")
    public void deleteCoupon(@PathVariable ("id") Long id) {
        couponService.deleteByIdCascadeEnable(id);
    }

    @PutMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public void updateCoupon(@RequestBody CouponDto couponDto) {
        Coupon coupon = couponMapper.toModel(couponDto);
        couponService.update(coupon);
    }


}
