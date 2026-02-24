package com.monkcommerce.coupons.service.strategy;

import com.monkcommerce.coupons.model.CouponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponStrategyFactory {

    private final CartWiseCouponStrategy cartWise;
    private final ProductWiseCouponStrategy productWise;
    private final BxGyCouponStrategy bxgy;

    public CouponStrategyFactory(CartWiseCouponStrategy c,
                                 ProductWiseCouponStrategy p,
                                 BxGyCouponStrategy b) {
        this.cartWise = c;
        this.productWise = p;
        this.bxgy = b;
    }

    public CouponStrategy getStrategy(CouponType type) {
        return switch (type) {
            case CART_WISE -> cartWise;
            case PRODUCT_WISE -> productWise;
            case BXGY -> bxgy;
        };
    }
}