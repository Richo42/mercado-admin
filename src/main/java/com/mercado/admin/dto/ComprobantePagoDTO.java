package com.mercado.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ComprobantePagoDTO(
        String numeroComprobante,
        LocalDateTime fechaEmision,
        String puestoCodigo,
        String adminRegistro,
        BigDecimal montoTotalPagado,
        List<DetalleComprobanteDTO> detalle
) {}