package com.mercado.admin.dto;

import java.math.BigDecimal;

public record ReporteRecaudacionDTO(
        String periodo,
        BigDecimal totalEmitido,
        BigDecimal totalCobrado,
        BigDecimal totalPendiente,
        Long puestosConDeuda
) {}