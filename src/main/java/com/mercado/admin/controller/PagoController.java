package com.mercado.admin.controller;

import com.mercado.admin.dto.ComprobantePagoDTO;
import com.mercado.admin.dto.PagoRequestDTO;
import com.mercado.admin.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/total")
    public ResponseEntity<ComprobantePagoDTO> registrarPagoTotal(@RequestBody PagoRequestDTO request) {
        ComprobantePagoDTO comprobante = pagoService.registrarPagoTotal(
                request.puestoCodigo(),
                request.adminRegistro()
        );
        return ResponseEntity.ok(comprobante);
    }
}