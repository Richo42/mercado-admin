package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReciboDTO(
        Long id,
        @NotBlank String tipo,
        @NotBlank String categoria,
        @NotNull @Positive BigDecimal montoTotal,
        @NotNull LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        String archivoAdjunto,
        String puestoCodigo
) {}