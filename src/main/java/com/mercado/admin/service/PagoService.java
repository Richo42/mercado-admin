package com.mercado.admin.service;

import com.mercado.admin.entity.Cuota;
import com.mercado.admin.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final CuotaRepository cuotaRepository;

    @Transactional
    public String registrarPagoTotal(String puestoCodigo, String adminRegistro) {
        List<Cuota> pendientes = cuotaRepository.findByPuestoCodigoAndEstado(puestoCodigo, "PENDIENTE");

        if (pendientes.isEmpty()) {
            return "El puesto " + puestoCodigo + " no tiene deudas pendientes.";
        }

        LocalDateTime ahora = LocalDateTime.now();
        for (Cuota cuota : pendientes) {
            cuota.setEstado("PAGADO");
            cuota.setFechaPago(ahora.toLocalDate());
            cuota.setFechaRegistroPago(ahora);
            cuota.setAdminRegistro(adminRegistro);
        }

        cuotaRepository.saveAll(pendientes);
        return "Pago total registrado: " + pendientes.size() + " conceptos actualizados.";
    }
}