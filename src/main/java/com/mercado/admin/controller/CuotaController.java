package com.mercado.admin.controller;

import com.mercado.admin.dto.CuotaDTO;
import com.mercado.admin.repository.CuotaRepository;
import com.mercado.admin.service.CuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
@RequiredArgsConstructor
public class CuotaController {

    private final CuotaRepository cuotaRepository;

    @GetMapping("/puesto/{codigo}")
    public ResponseEntity<List<CuotaDTO>> verDeudasPuesto(@PathVariable String codigo) {
        // Llamamos al nuevo método del repositorio
        List<CuotaDTO> deudas = cuotaRepository.findDetalleCuotasByPuestoCodigo(codigo);

        if (deudas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deudas);
    }
}