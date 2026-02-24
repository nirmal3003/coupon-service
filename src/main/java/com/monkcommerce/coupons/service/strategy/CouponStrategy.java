package com.monkcommerce.coupons.service.strategy;

import com.monkcommerce.coupons.model.Cart;
import com.monkcommerce.coupons.model.Coupon;
import com.monkcommerce.coupons.model.DiscountResult;

public interface CouponStrategy {
    DiscountResult apply(Coupon coupon, Cart cart);
}