package com.mercado.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CuotaDTO(
        Long id,
        String puestoCodigo,
        String tipoServicio,
        String categoria,
        BigDecimal monto,
        String estado,
        String periodo,
        LocalDate fechaPago,
        String adminRegistro
) {}