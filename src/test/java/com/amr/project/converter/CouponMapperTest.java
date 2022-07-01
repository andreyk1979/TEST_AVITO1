package com.amr.project.converter;

import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponMapperTest {

    @Autowired
    private CouponMapper couponMapperTest;

    @Test
    public void toDto() {
        Coupon coupon = new Coupon();
        User user = new User();
        user.setId(1L);
        coupon.setId(1L);
        coupon.setUser(user);
        CouponDto couponDto = couponMapperTest.toDto(coupon);
        Assertions.assertNotNull(couponDto);
        Assertions.assertEquals(coupon.getId(), couponDto.getId());
        Assertions.assertEquals(coupon.getUser().getId(), couponDto.getUserId());
    }

    @Test
    public void toModel() {
        CouponDto couponDto = new CouponDto();
        User user = new User();
        user.setId(4L);
        couponDto.setId(6L);
        couponDto.setUserId(4L);
        Coupon coupon = couponMapperTest.toModel(couponDto);
        coupon.setUser(user);
        Assertions.assertNotNull(coupon);
        Assertions.assertEquals(couponDto.getId(), coupon.getId());
        Assertions.assertEquals(couponDto.getUserId(), coupon.getUser().getId());
    }
}
