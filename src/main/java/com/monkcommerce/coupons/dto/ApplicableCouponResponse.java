package com.monkcommerce.coupons.dto;

import com.monkcommerce.coupons.model.CouponType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableCouponResponse {
    private Long couponId;
    private CouponType type;
    private Double discount;
}