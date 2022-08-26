package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.CouponRestFacade;
import com.amr.project.model.dto.CouponDto;

@RestController
@RequestMapping("api/coupon")
public class CouponRestController {

    private final CouponRestFacade couponRestFacade;

    public CouponRestController(CouponRestFacade couponRestFacade) {
        this.couponRestFacade = couponRestFacade;
    }

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public CouponDto createDiscount(@RequestBody CouponDto couponDto) {
        return couponRestFacade.createDiscount(couponDto);
    }

    @GetMapping("/coupon")
    @ResponseStatus(HttpStatus.OK)
    public List<CouponDto> allCoupons(){
        return couponRestFacade.allCoupons();
    }

    @DeleteMapping("/coupon/{id}")
    public void deleteCoupon(@PathVariable ("id") Long id) {
        couponRestFacade.deleteCoupon(id);
    }

    @PutMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public void updateCoupon(@RequestBody CouponDto couponDto) {
        couponRestFacade.updateCoupon(couponDto);
    }


}
