package com.monkcommerce.coupons.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResult {
    private Double discountAmount;
    private boolean applicable;
}
