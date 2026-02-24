package com.monkcommerce.coupons.service.strategy;

import com.monkcommerce.coupons.model.Cart;
import com.monkcommerce.coupons.model.CartItem;
import com.monkcommerce.coupons.model.Coupon;
import com.monkcommerce.coupons.model.DiscountResult;
import org.springframework.stereotype.Component;

@Component
public class BxGyCouponStrategy implements CouponStrategy {

    @Override
    public DiscountResult apply(Coupon coupon, Cart cart) {

        int buyCount = 0;
        int getCount = 0;
        double getPriceTotal = 0;

        for (CartItem item : cart.getItems()) {
            if (coupon.getBuyProductIds() != null &&
                    coupon.getBuyProductIds().contains(item.getProductId())) {
                buyCount += item.getQuantity();
            }

            if (coupon.getGetProductIds() != null &&
                    coupon.getGetProductIds().contains(item.getProductId())) {
                getCount += item.getQuantity();
                getPriceTotal += item.getPrice() * item.getQuantity();
            }
        }

        int possibleApplications = buyCount / coupon.getBuyQuantity();
        int applicableTimes = Math.min(possibleApplications,
                coupon.getRepetitionLimit() == null ?
                        possibleApplications :
                        coupon.getRepetitionLimit());

        if (applicableTimes <= 0 || getCount == 0)
            return new DiscountResult(0.0, false);

        double discount = (getPriceTotal / getCount)
                * Math.min(applicableTimes * coupon.getGetQuantity(), getCount);

        return new DiscountResult(discount, true);
    }
}