package com.monkcommerce.coupons.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private List<CartItem> items;
}
