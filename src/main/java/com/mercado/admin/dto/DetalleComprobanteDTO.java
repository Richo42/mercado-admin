package com.mercado.admin.dto;

import java.math.BigDecimal;

public record DetalleComprobanteDTO(
        String tipoServicio,
        String periodo,
        BigDecimal monto
) {}