package com.coupon.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayloadRequest(
        @NotBlank(message = "O código é obrigatório.")
        @Size(min = 6, max = 6, message = "O código deve ter exatamente 6 caracteres após a limpeza.") // A validação de 6 caracteres é feita aqui, mas a limpeza é na Service.
        String code,

        @NotBlank(message = "A descrição é obrigatória.")
        String description,

        @NotNull(message = "O valor de desconto é obrigatório.")
        @DecimalMin(value = "0.5", message = "O desconto deve ser de no mínimo 0.5.")
        BigDecimal discountValue,

        @NotNull(message = "A data de expiração é obrigatória.")
        @Future(message = "A data de expiração deve ser futura.") // Garante que a data não seja no passado
        LocalDate expirationDate,
        
        Boolean published
) {
}
