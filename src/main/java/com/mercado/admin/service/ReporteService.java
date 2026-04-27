package com.mercado.admin.service;

import com.mercado.admin.dto.ReporteRecaudacionDTO;
import com.mercado.admin.repository.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final CuotaRepository cuotaRepository;

    @Transactional(readOnly = true)
    public List<ReporteRecaudacionDTO> reporteRecaudacionFiltrado(String desde, String hasta) {
        return cuotaRepository.obtenerRecaudacionPorPeriodo(desde, hasta);
    }
}