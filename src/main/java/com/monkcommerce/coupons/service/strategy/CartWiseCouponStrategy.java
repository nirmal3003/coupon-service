package com.monkcommerce.coupons.service.strategy;

import com.monkcommerce.coupons.model.Cart;
import com.monkcommerce.coupons.model.Coupon;
import com.monkcommerce.coupons.model.DiscountResult;
import org.springframework.stereotype.Component;

@Component
public class CartWiseCouponStrategy implements CouponStrategy {

    @Override
    public DiscountResult apply(Coupon coupon, Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        if (total > coupon.getThreshold()) {
            double discount = total * coupon.getDiscount() / 100;
            return new DiscountResult(discount, true);
        }
        return new DiscountResult(0.0, false);
    }
}