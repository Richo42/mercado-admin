package com.mercado.admin.controller;

import com.mercado.admin.dto.ReciboDTO;
import com.mercado.admin.entity.Recibo;
import com.mercado.admin.service.ReciboService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recibos")
@RequiredArgsConstructor
public class ReciboController {

    private final ReciboService reciboService;

    @GetMapping
    public ResponseEntity<List<?>> listar() {
        return ResponseEntity.ok(reciboService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody ReciboDTO dto,
            @AuthenticationPrincipal String adminRegistro) { //  Extrae el username del token JWT
        try {
            // ✅ Pasamos el usuario autenticado al service
            ReciboDTO creado = reciboService.crearRecibo(dto, adminRegistro);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear recibo: " + e.getMessage());
        }
    }
}