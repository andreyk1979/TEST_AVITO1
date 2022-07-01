package com.amr.project.webapp.controller;


import com.amr.project.service.abstracts.CouponService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CouponRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CouponService couponService;

    @Test
    public void shouldCreateMock() throws Exception {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void deleteCoupon() throws Exception {
        mockMvc.perform(delete("/api/coupon/coupon/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void allCoupons() throws Exception {
        mockMvc.perform(get("/api/coupon/coupon"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", Matchers.is(couponService.findAll().size())));
    }

    @Test
    public void addCoupon() throws Exception {
        int countCoupon = couponService.findAll().size();
        mockMvc.perform(put("/api/coupon/coupons")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\"start\": \"2022-07-23T11:16:15\",\n" +
                                "\"userId\": \"2\"\n" +
                                "}"))
                .andExpect(status().isOk());
        assertThat(countCoupon + 1).isEqualTo(couponService.findAll().size());
    }
}
