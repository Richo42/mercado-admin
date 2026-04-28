package com.mercado.admin.service;

import com.mercado.admin.dto.ReporteRecaudacionDTO;
import com.mercado.admin.entity.Puesto;
import com.mercado.admin.repository.CuotaRepository;
import com.mercado.admin.repository.PuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final CuotaRepository cuotaRepository;
    private final PuestoRepository puestoRepository;  // ✅ Agregado

    @Transactional(readOnly = true)
    public List<ReporteRecaudacionDTO> reporteRecaudacionFiltrado(String desde, String hasta) {
        return cuotaRepository.obtenerRecaudacionPorPeriodo(desde, hasta);
    }

    public List<Map<String, Object>> obtenerPuestosConEstadoDeuda() {
        List<Puesto> puestos = puestoRepository.findAll();

        return puestos.stream().map(p -> {
            BigDecimal deuda = cuotaRepository.findDeudaPendienteByPuesto(p.getCodigo());
            BigDecimal montoDeuda = deuda != null ? deuda : BigDecimal.ZERO;
            String estado = montoDeuda.compareTo(BigDecimal.ZERO) > 0 ? "PENDIENTE" : "AL DÍA";

            // ✅ Usamos HashMap para evitar el error de inferencia de tipos
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("codigo", p.getCodigo());
            resultado.put("duenoRuc", p.getDuenoActual().getDniRuc());
            resultado.put("duenoNombre", p.getDuenoActual().getNombreRazonSocial());
            resultado.put("totalDeuda", montoDeuda);
            resultado.put("estado", estado);

            return resultado;
        }).toList();
    }
}