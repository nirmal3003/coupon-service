package com.monkcommerce.coupons.service;

import com.monkcommerce.coupons.dto.ApplicableCouponResponse;
import com.monkcommerce.coupons.dto.CouponRequest;
import com.monkcommerce.coupons.model.Cart;
import com.monkcommerce.coupons.model.Coupon;
import com.monkcommerce.coupons.model.CouponApplicationResult;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponRequest request);

    List<Coupon> getAllCoupons();

    Coupon getCoupon(Long id);

    Coupon updateCoupon(Long id, CouponRequest request);

    void deleteCoupon(Long id);

    List<ApplicableCouponResponse> getApplicableCoupons(Cart cart);

    CouponApplicationResult applyCoupon(Long id, Cart cart);
}
