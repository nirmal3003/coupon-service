package com.monkcommerce.coupons.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkcommerce.coupons.dto.*;
import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.service.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createCoupon() throws Exception {
        CouponRequest req = new CouponRequest();
        req.setType(CouponType.CART_WISE);
        req.setThreshold(100.0);
        req.setDiscount(10.0);

        Coupon coupon = new Coupon();
        coupon.setId(1L);

        when(service.createCoupon(any(CouponRequest.class)))
                .thenReturn(coupon);
        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllCoupons() throws Exception {
        Coupon c = new Coupon();
        c.setId(1L);

        when(service.getAllCoupons()).thenReturn(List.of(c));

        mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getCouponById() throws Exception {
        Coupon c = new Coupon();
        c.setId(1L);

        when(service.getCoupon(1L)).thenReturn(c);

        mockMvc.perform(get("/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateCoupon() throws Exception {
        CouponRequest req = new CouponRequest();
        req.setType(CouponType.CART_WISE);
        req.setThreshold(200.0);
        req.setDiscount(15.0);

        Coupon updated = new Coupon();
        updated.setId(1L);

        when(service.updateCoupon(1L, req)).thenReturn(updated);

        mockMvc.perform(put("/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCoupon() throws Exception {
        doNothing().when(service).deleteCoupon(1L);

        mockMvc.perform(delete("/coupons/1"))
                .andExpect(status().isOk());
    }

    @Test
    void applicableCoupons() throws Exception {
        CartRequest req = new CartRequest();
        req.setCart(new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        )));

        ApplicableCouponResponse resp =
                new ApplicableCouponResponse(1L,
                        CouponType.CART_WISE,
                        20.0);

        when(service.getApplicableCoupons(any(Cart.class)))
                .thenReturn(List.of(resp));

        mockMvc.perform(post("/coupons/applicable-coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].couponId").value(1));
    }

    @Test
    void applyCoupon() throws Exception {
        CartRequest req = new CartRequest();
        req.setCart(new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        )));

        CouponApplicationResult result =
                new CouponApplicationResult(req.getCart(), 200.0, 20.0, 180.0);

        when(service.applyCoupon(eq(1L), any(Cart.class)))
                .thenReturn(result);

        mockMvc.perform(post("/coupons/apply-coupon/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice").value(180.0));
    }
}