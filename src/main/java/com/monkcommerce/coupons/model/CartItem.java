package com.monkcommerce.coupons.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @NotNull
    private Long productId;
    @Positive
    private Integer quantity;
    @Positive
    private Double price;
}
