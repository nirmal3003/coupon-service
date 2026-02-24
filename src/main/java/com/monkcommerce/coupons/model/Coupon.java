package com.monkcommerce.coupons.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private Double threshold;
    private Double discount;
    private Long productId;

    @ElementCollection
    private List<Long> buyProductIds;

    @ElementCollection
    private List<Long> getProductIds;

    private Integer buyQuantity;
    private Integer getQuantity;
    private Integer repetitionLimit;

    private LocalDateTime expiryDate;
}
