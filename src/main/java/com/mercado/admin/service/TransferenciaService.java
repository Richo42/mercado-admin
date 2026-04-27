package com.mercado.admin.service;

import com.mercado.admin.entity.Cuota;
import com.mercado.admin.entity.Dueno;
import com.mercado.admin.entity.Puesto;
import com.mercado.admin.repository.CuotaRepository;
import com.mercado.admin.repository.DuenoRepository;
import com.mercado.admin.repository.PuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final PuestoRepository puestoRepository;
    private final DuenoRepository duenoRepository;
    private final CuotaRepository cuotaRepository;

    @Transactional
    public String transferirPuesto(String codigoPuesto, String nuevoDuenoRuc) {

        List<Cuota> pendientes = cuotaRepository.findByPuestoCodigoAndEstado(codigoPuesto, "PENDIENTE");
        List<Cuota> vencidos = cuotaRepository.findByPuestoCodigoAndEstado(codigoPuesto, "VENCIDO");

        if (!pendientes.isEmpty() || !vencidos.isEmpty()) {
            return "No se puede transferir: el puesto tiene cuotas pendientes o vencidas.";
        }

        Dueno nuevoDueno = duenoRepository.findById(nuevoDuenoRuc)
                .orElseThrow(() -> new IllegalArgumentException("El DNI/RUC del nuevo dueño no existe"));

        Puesto puesto = puestoRepository.findById(codigoPuesto)
                .orElseThrow(() -> new IllegalArgumentException("El puesto no existe"));

        puesto.setDuenoActual(nuevoDueno);
        puestoRepository.save(puesto);

        return "Transferencia exitosa. Nuevo dueño: " + nuevoDueno.getNombreRazonSocial();
    }
}