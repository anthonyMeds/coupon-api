package com.coupon.api.service;

import com.coupon.api.config.exception.ServiceException;
import com.coupon.api.dto.PayloadRequest;
import com.coupon.api.dto.PayloadResponse;
import com.coupon.api.entity.Coupon;
import com.coupon.api.repository.CouponRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private PayloadRequest requestValido;
    @Mock
    private Coupon cupomCadastrado;

    @BeforeEach
    void setUp() {

        requestValido = new PayloadRequest(
                "ABC-123",
                "Desconto de Teste",
                BigDecimal.valueOf(0.8),
                LocalDateTime.now().plusDays(1),
                null
        );
        cupomCadastrado = new Coupon();
        cupomCadastrado.setId(1L);
        cupomCadastrado.setCode("ABC123");
        cupomCadastrado.setDescription(requestValido.description());
        cupomCadastrado.setDiscountValue(requestValido.discountValue());
        cupomCadastrado.setExpirationDate(requestValido.expirationDate());
        cupomCadastrado.setPublished(false);
    }


    @Test
    @DisplayName("Deve criar cupom com sucesso quando o código é limpo e published é null")
    void deveCriarCupomSeCodigoLimpoENaoForPublicado() throws ServiceException {

        when(couponRepository.existsByCode("ABC123")).thenReturn(false);
        when(couponRepository.save(any(Coupon.class))).thenReturn(cupomCadastrado);

        PayloadResponse response = couponService.createCoupon(requestValido);

        assertNotNull(response);
        assertEquals("ABC123", response.code());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    @DisplayName("Deve criar cupom com sucesso quando published é true")
    void deveCriarCupomSeCodigoForLimpoEJaEstiverPublicado() throws ServiceException {

        PayloadRequest request = new PayloadRequest(
                "XYZ/456",
                "Desconto publicado",
                BigDecimal.valueOf(1.0),
                LocalDateTime.now().plusDays(5),
                true
        );


        Coupon publishedCoupon = new Coupon();
        publishedCoupon.setCode("XYZ456");
        publishedCoupon.setPublished(true);
        publishedCoupon.setId(1L);

        when(couponRepository.existsByCode("XYZ456")).thenReturn(false);
        when(couponRepository.save(any(Coupon.class))).thenReturn(publishedCoupon);

        PayloadResponse response = couponService.createCoupon(request);

        assertNotNull(response);
        assertEquals("XYZ456", response.code());
        assertTrue(response.published());
    }



    @Test
    @DisplayName("Deve lançar ServiceException quando o código limpo já existe")
    void deveBarrarQuandoCodigoDuplicado() {

        String codigoDuplicado = "ABC123";

        when(couponRepository.existsByCode(codigoDuplicado)).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            couponService.createCoupon(requestValido);
        });

        assertTrue(exception.getMessage().contains("Já existe um cupom cadastrado com o código"));
        verify(couponRepository, never()).save(any(Coupon.class));
    }

}