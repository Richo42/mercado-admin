package com.mercado.admin.controller;

import com.mercado.admin.dto.ReporteRecaudacionDTO;
import com.mercado.admin.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/recaudacion")
    public ResponseEntity<List<ReporteRecaudacionDTO>> obtenerRecaudacion(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) {

        return ResponseEntity.ok(reporteService.reporteRecaudacionFiltrado(desde, hasta));
    }
}