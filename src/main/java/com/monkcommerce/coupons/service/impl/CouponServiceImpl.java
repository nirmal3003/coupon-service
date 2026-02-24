package com.monkcommerce.coupons.service.impl;

import com.monkcommerce.coupons.dto.*;
import com.monkcommerce.coupons.exception.CouponNotFoundException;
import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.repository.CouponRepository;
import com.monkcommerce.coupons.service.CouponService;
import com.monkcommerce.coupons.service.strategy.CouponStrategy;
import com.monkcommerce.coupons.service.strategy.CouponStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository repository;
    private final CouponStrategyFactory factory;

    public CouponServiceImpl(CouponRepository repository,
                             CouponStrategyFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Coupon createCoupon(CouponRequest r) {
        Coupon c = new Coupon();
        c.setType(r.getType());
        c.setThreshold(r.getThreshold());
        c.setDiscount(r.getDiscount());
        c.setProductId(r.getProductId());
        c.setBuyProductIds(r.getBuyProductIds());
        c.setGetProductIds(r.getGetProductIds());
        c.setBuyQuantity(r.getBuyQuantity());
        c.setGetQuantity(r.getGetQuantity());
        c.setRepetitionLimit(r.getRepetitionLimit());
        return repository.save(c);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return repository.findAll();
    }

    @Override
    public Coupon getCoupon(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    @Override
    public Coupon updateCoupon(Long id, CouponRequest r) {
        Coupon c = getCoupon(id);
        c.setType(r.getType());
        c.setThreshold(r.getThreshold());
        c.setDiscount(r.getDiscount());
        c.setProductId(r.getProductId());
        c.setBuyProductIds(r.getBuyProductIds());
        c.setGetProductIds(r.getGetProductIds());
        c.setBuyQuantity(r.getBuyQuantity());
        c.setGetQuantity(r.getGetQuantity());
        c.setRepetitionLimit(r.getRepetitionLimit());
        return repository.save(c);
    }

    @Override
    public void deleteCoupon(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ApplicableCouponResponse> getApplicableCoupons(Cart cart) {
        return repository.findAll().stream()
                .map(c -> {
                    CouponStrategy strategy = factory.getStrategy(c.getType());
                    DiscountResult result = strategy.apply(c, cart);
                    if (result.isApplicable())
                        return new ApplicableCouponResponse(
                                c.getId(), c.getType(),
                                result.getDiscountAmount());
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public CouponApplicationResult applyCoupon(Long id, Cart cart) {
        Coupon coupon = getCoupon(id);
        CouponStrategy strategy = factory.getStrategy(coupon.getType());
        DiscountResult result = strategy.apply(coupon, cart);

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        double finalPrice = total - result.getDiscountAmount();

        return new CouponApplicationResult(
                cart,
                total,
                result.getDiscountAmount(),
                finalPrice
        );
    }
}