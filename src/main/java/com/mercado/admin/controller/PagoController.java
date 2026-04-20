package com.mercado.admin.controller;

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
    public ResponseEntity<String> pagarTodo(@RequestBody PagoRequestDTO request) {
        if (request.puestoCodigo() == null || request.adminRegistro() == null) {
            return ResponseEntity.badRequest().body("Puesto y administrador son obligatorios");
        }

        try {
            String mensaje = pagoService.registrarPagoTotal(
                    request.puestoCodigo(),
                    request.adminRegistro()
            );
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}