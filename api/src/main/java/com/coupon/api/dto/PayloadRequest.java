package com.coupon.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayloadRequest(
        @NotBlank(message = "O código é obrigatório.")
        @Size(min = 6, message = "O código deve ter exatamente 6 caracteres após a limpeza.")
        @Schema(example = "ABC-123",
                description = "Código alfanumérico. Caracteres especiais serão removidos para garantir 6 caracteres finais.")
        String code,

        @NotBlank(message = "A descrição é obrigatória.")
        @Schema(example = "Desconto de 80% na primeira compra.",
                description = "Breve descrição do cupom.")
        String description,

        @NotNull(message = "O valor de desconto é obrigatório.")
        @DecimalMin(value = "0.5", message = "O desconto deve ser de no mínimo 0.5.")
        @Schema(example = "0.8",
                description = "Valor do desconto. Deve ser no mínimo 0.5.")
        BigDecimal discountValue,

        @NotNull(message = "A data de expiração é obrigatória.")
        @Future(message = "A data de expiração deve ser futura.")
        @Schema(example = "2026-12-31T23:59:59",
                description = "Data e hora de expiração no futuro (formato ISO 8601).")
        LocalDateTime expirationDate,

        @Schema(example = "false",
                description = "Indica se o cupom deve ser publicado imediatamente. Padrão: false.")
        Boolean published
) {
}
