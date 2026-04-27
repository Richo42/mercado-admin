package com.mercado.admin.service;

import com.mercado.admin.dto.ComprobantePagoDTO;
import com.mercado.admin.dto.DetalleComprobanteDTO;
import com.mercado.admin.entity.CorrelativoComprobante;
import com.mercado.admin.entity.Cuota;
import com.mercado.admin.repository.CorrelativoRepository;
import com.mercado.admin.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final CuotaRepository cuotaRepository;
    private final CorrelativoRepository correlativoRepository;

    @Transactional
    public ComprobantePagoDTO registrarPagoTotal(String puestoCodigo, String adminRegistro) {
        List<Cuota> pendientes = cuotaRepository.findByPuestoCodigoAndEstado(puestoCodigo, "PENDIENTE");

        if (pendientes.isEmpty()) {
            throw new IllegalArgumentException("El puesto " + puestoCodigo + " no tiene deudas pendientes.");
        }

        LocalDateTime ahora = LocalDateTime.now();
        BigDecimal totalPagado = BigDecimal.ZERO;
        List<DetalleComprobanteDTO> detalle = new ArrayList<>();

        for (Cuota cuota : pendientes) {
            cuota.setEstado("PAGADO");
            cuota.setFechaPago(ahora.toLocalDate());
            cuota.setFechaRegistroPago(ahora);
            cuota.setAdminRegistro(adminRegistro);

            totalPagado = totalPagado.add(cuota.getMonto());
            detalle.add(new DetalleComprobanteDTO(
                    cuota.getRecibo().getTipo(),
                    cuota.getPeriodo(),
                    cuota.getMonto()
            ));
        }

        cuotaRepository.saveAll(pendientes);

        // GENERACIÓN SEGURA DE CORRELATIVO
        int anioActual = Year.now().getValue();
        CorrelativoComprobante correlativo = correlativoRepository.findByAnioForUpdate(anioActual)
                .orElse(new CorrelativoComprobante(anioActual, 0L));

        correlativo.setUltimoNumero(correlativo.getUltimoNumero() + 1);
        correlativoRepository.save(correlativo);

        // Formato: CP-2024-000001
        String numeroComprobante = String.format("CP-%d-%06d", anioActual, correlativo.getUltimoNumero());

        return new ComprobantePagoDTO(
                numeroComprobante,
                ahora,
                puestoCodigo,
                adminRegistro,
                totalPagado,
                detalle
        );
    }
}