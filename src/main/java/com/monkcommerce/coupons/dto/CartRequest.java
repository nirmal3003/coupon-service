package com.monkcommerce.coupons.dto;

import com.monkcommerce.coupons.model.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class CartRequest {
    @NotNull
    @Valid
    private Cart cart;
}