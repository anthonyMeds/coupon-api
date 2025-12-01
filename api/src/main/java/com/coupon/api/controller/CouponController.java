package com.coupon.api.controller;

import com.coupon.api.config.exception.ServiceException;
import com.coupon.api.dto.PayloadRequest;
import com.coupon.api.dto.PayloadResponse;
import com.coupon.api.entity.Coupon;
import com.coupon.api.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping
    public ResponseEntity<PayloadResponse> create(@Valid @RequestBody PayloadRequest request) throws ServiceException {
        PayloadResponse createdCoupon = couponService.createCoupon(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }

}
