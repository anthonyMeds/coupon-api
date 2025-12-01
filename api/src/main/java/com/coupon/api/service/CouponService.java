package com.coupon.api.service;

import com.coupon.api.config.exception.ServiceException;
import com.coupon.api.dto.CouponStatus;
import com.coupon.api.dto.PayloadRequest;
import com.coupon.api.dto.PayloadResponse;
import com.coupon.api.entity.Coupon;
import com.coupon.api.repository.CouponRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public PayloadResponse createCoupon(@Valid PayloadRequest request) throws ServiceException {

        // Retira qualquer caractere alfaumérico
        String codigoTratado = request.code()
                .toUpperCase()
                .replaceAll("[^a-zA-Z0-9]", "");

        if (couponRepository.existsByCode(codigoTratado)) {
            throw new ServiceException("Já existe um cupom cadastrado com o código: " + codigoTratado);
        }

        Coupon coupon = new Coupon();

        coupon.setCode(codigoTratado);
        coupon.setDescription(request.description());
        coupon.setDiscountValue(request.discountValue());
        coupon.setExpirationDate(request.expirationDate());

        if (request.published() != null) {
            coupon.setPublished(request.published());
        }

        Coupon novoCupom = couponRepository.save(coupon);

        return toResponse(novoCupom);

    }

    public PayloadResponse findById(Long id) throws ServiceException {

        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cupom não encontrado com ID: " + id));

        return toResponse(coupon);
    }

    private PayloadResponse toResponse(Coupon coupon) {
        return new PayloadResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus().toString(),
                coupon.getPublished(),
                coupon.getRedeemed()
        );
    }


}
