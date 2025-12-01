package com.coupon.api.entity;

import com.coupon.api.dto.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bd gerencia ID
    private Long id;

    @Column(length=6, nullable=false, unique = true)
    private String code;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private BigDecimal discountValue;

    @Column(nullable=false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private Boolean published = false;

    @Column(nullable = false)
    private Boolean redeemed = true;

    @Column(nullable=false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private CouponStatus status = CouponStatus.ACTIVE;

}
