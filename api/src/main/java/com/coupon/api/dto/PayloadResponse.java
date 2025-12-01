package com.coupon.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayloadResponse(
        Long id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        Boolean published,
        Boolean redeemed,
        String status
) {
}
