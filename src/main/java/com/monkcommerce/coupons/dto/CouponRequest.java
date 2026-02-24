package com.monkcommerce.coupons.dto;

import com.monkcommerce.coupons.model.CouponType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;
@Getter
@Setter
public class CouponRequest {
    @NotNull(message = "Coupon type is required")
    private CouponType type;
    @Positive(message = "Threshold must be positive")
    private Double threshold;
    @Positive(message = "Discount must be positive")
    private Double discount;
    private Long productId;
    private List<Long> buyProductIds;
    private List<Long> getProductIds;
    @Positive(message = "Buy quantity must be positive")
    private Integer buyQuantity;
    @Positive(message = "Get quantity must be positive")
    private Integer getQuantity;
    @PositiveOrZero(message = "Repetition limit cannot be negative")
    private Integer repetitionLimit;
}
