package com.monkcommerce.coupons.strategy;

import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.service.strategy.ProductWiseCouponStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductWiseCouponStrategyTest {

    private final ProductWiseCouponStrategy strategy = new ProductWiseCouponStrategy();

    @Test
    void applyDiscount() {
        Coupon coupon = new Coupon();
        coupon.setProductId(1L);
        coupon.setDiscount(20.0);

        Cart cart = new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        ));

        DiscountResult result = strategy.apply(coupon, cart);

        assertTrue(result.isApplicable());
        assertEquals(40.0, result.getDiscountAmount());
    }
}