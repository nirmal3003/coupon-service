package com.monkcommerce.coupons.controller;

import com.monkcommerce.coupons.dto.*;
import com.monkcommerce.coupons.model.*;
import com.monkcommerce.coupons.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @PostMapping
    public Coupon create(@RequestBody @Valid CouponRequest request) {

        return service.createCoupon(request);
    }

    @GetMapping
    public List<Coupon> getAll() {

        return service.getAllCoupons();
    }

    @GetMapping("/{id}")
    public Coupon get(@PathVariable Long id) {

        return service.getCoupon(id);
    }

    @PutMapping("/{id}")
    public Coupon update(@PathVariable Long id,
                         @RequestBody CouponRequest request) {
        return service.updateCoupon(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        service.deleteCoupon(id);
    }

    @PostMapping("/applicable-coupons")
    public List<ApplicableCouponResponse> applicable(
            @RequestBody @Valid CartRequest request) {
        return service.getApplicableCoupons(request.getCart());
    }

    @PostMapping("/apply-coupon/{id}")
    public CouponApplicationResult apply(
            @PathVariable Long id,
            @RequestBody @Valid CartRequest request) {
        return service.applyCoupon(id, request.getCart());
    }
}