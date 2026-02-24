package com.monkcommerce.coupons.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponApplicationResult {
    private Cart updatedCart;
    private Double totalPrice;
    private Double totalDiscount;
    private Double finalPrice;
}