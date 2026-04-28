package com.mercado.admin.controller;

import com.mercado.admin.dto.ReporteRecaudacionDTO;
import com.mercado.admin.repository.DuenoRepository;
import com.mercado.admin.repository.ReciboRepository;
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
    private final DuenoRepository duenoRepository;
    private final ReciboRepository reciboRepository;

    @GetMapping("/recaudacion")
    public ResponseEntity<List<ReporteRecaudacionDTO>> obtenerRecaudacion(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) {
        return ResponseEntity.ok(reporteService.reporteRecaudacionFiltrado(desde, hasta));
    }

    // ✅ NUEVO: Puestos con estado de deuda (usa obtenerPuestosConEstadoDeuda)
    @GetMapping("/puestos-estado")
    public ResponseEntity<List<?>> obtenerPuestosConEstado() {
        return ResponseEntity.ok(reporteService.obtenerPuestosConEstadoDeuda());
    }

    // ✅ NUEVO: Listado completo de dueños
    @GetMapping("/listado-duenos")
    public ResponseEntity<List<?>> listarDuenos() {
        return ResponseEntity.ok(duenoRepository.findAll());
    }

    // ✅ NUEVO: Listado completo de recibos
    @GetMapping("/listado-recibos")
    public ResponseEntity<List<?>> listarRecibos() {
        return ResponseEntity.ok(reciboRepository.findAll());
    }
}