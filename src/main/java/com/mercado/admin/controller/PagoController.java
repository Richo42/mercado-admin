package com.mercado.admin.controller;

import com.mercado.admin.dto.ComprobantePagoDTO;
import com.mercado.admin.dto.PagoRequestDTO;
import com.mercado.admin.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/total")
    public ResponseEntity<ComprobantePagoDTO> registrarPagoTotal(@RequestBody PagoRequestDTO request) {
        // Extraer usuario logueado del token JWT
        String adminRegistro = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        ComprobantePagoDTO comprobante = pagoService.registrarPagoTotal(
                request.puestoCodigo(),
                adminRegistro  // Usar usuario autenticado
        );
        return ResponseEntity.ok(comprobante);
    }
}