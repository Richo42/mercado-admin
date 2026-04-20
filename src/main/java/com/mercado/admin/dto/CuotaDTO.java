package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CuotaDTO(
        @NotNull Long id,
        @NotBlank String puestoCodigo,
        @NotNull Long reciboId,
        @NotNull @Positive BigDecimal monto,
        @NotBlank String estado,
        LocalDate fechaPago,
        LocalDateTime fechaRegistroPago,
        String adminRegistro,
        @NotBlank String periodo
) {}