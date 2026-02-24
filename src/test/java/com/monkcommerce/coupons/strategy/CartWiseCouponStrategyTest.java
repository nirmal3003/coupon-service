package com.monkcommerce.coupons.strategy;

import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.service.strategy.CartWiseCouponStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartWiseCouponStrategyTest {

    private final CartWiseCouponStrategy strategy = new CartWiseCouponStrategy();

    @Test
    void applyDiscount() {
        Coupon coupon = new Coupon();
        coupon.setThreshold(100.0);
        coupon.setDiscount(10.0);

        Cart cart = new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        ));

        DiscountResult result = strategy.apply(coupon, cart);

        assertTrue(result.isApplicable());
        assertEquals(20.0, result.getDiscountAmount());
    }
}