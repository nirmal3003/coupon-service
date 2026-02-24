package com.monkcommerce.coupons.strategy;

import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.service.strategy.BxGyCouponStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BxGyCouponStrategyTest {

    private final BxGyCouponStrategy strategy = new BxGyCouponStrategy();

    @Test
    void applyBxGy() {
        Coupon coupon = new Coupon();
        coupon.setBuyProductIds(List.of(1L));
        coupon.setGetProductIds(List.of(2L));
        coupon.setBuyQuantity(2);
        coupon.setGetQuantity(1);
        coupon.setRepetitionLimit(2);

        Cart cart = new Cart(List.of(
                new CartItem(1L, 4, 100.0),
                new CartItem(2L, 2, 50.0)
        ));

        DiscountResult result = strategy.apply(coupon, cart);

        assertTrue(result.isApplicable());
        assertEquals(100.0, result.getDiscountAmount());
    }
}