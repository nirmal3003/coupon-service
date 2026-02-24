package com.monkcommerce.coupons.service.strategy;

import com.monkcommerce.coupons.model.Cart;
import com.monkcommerce.coupons.model.CartItem;
import com.monkcommerce.coupons.model.Coupon;
import com.monkcommerce.coupons.model.DiscountResult;
import org.springframework.stereotype.Component;

@Component
public class ProductWiseCouponStrategy implements CouponStrategy {

    @Override
    public DiscountResult apply(Coupon coupon, Cart cart) {
        double discount = 0;

        for (CartItem item : cart.getItems()) {
            if (item.getProductId().equals(coupon.getProductId())) {
                discount += item.getPrice() * item.getQuantity()
                        * coupon.getDiscount() / 100;
            }
        }

        return new DiscountResult(discount, discount > 0);
    }
}
