package com.monkcommerce.coupons.service;

import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.repository.CouponRepository;
import com.monkcommerce.coupons.service.impl.CouponServiceImpl;
import com.monkcommerce.coupons.service.strategy.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponServiceImplTest {

    @Test
    void getApplicableCoupons() {
        CouponRepository repo = mock(CouponRepository.class);

        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setType(CouponType.CART_WISE);
        coupon.setThreshold(100.0);
        coupon.setDiscount(10.0);

        when(repo.findAll()).thenReturn(List.of(coupon));

        CouponStrategyFactory factory =
                new CouponStrategyFactory(
                        new CartWiseCouponStrategy(),
                        new ProductWiseCouponStrategy(),
                        new BxGyCouponStrategy()
                );

        CouponServiceImpl service = new CouponServiceImpl(repo, factory);

        Cart cart = new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        ));

        var result = service.getApplicableCoupons(cart);

        assertEquals(1, result.size());
    }

    @Test
    void applyCoupon() {
        CouponRepository repo = mock(CouponRepository.class);

        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setType(CouponType.CART_WISE);
        coupon.setThreshold(100.0);
        coupon.setDiscount(10.0);

        when(repo.findById(1L)).thenReturn(Optional.of(coupon));

        CouponStrategyFactory factory =
                new CouponStrategyFactory(
                        new CartWiseCouponStrategy(),
                        new ProductWiseCouponStrategy(),
                        new BxGyCouponStrategy()
                );

        CouponServiceImpl service = new CouponServiceImpl(repo, factory);

        Cart cart = new Cart(List.of(
                new CartItem(1L, 2, 100.0)
        ));

        var result = service.applyCoupon(1L, cart);

        assertEquals(200.0, result.getTotalPrice());
        assertEquals(20.0, result.getTotalDiscount());
        assertEquals(180.0, result.getFinalPrice());
    }
}