package com.mercado.admin.controller;

import com.mercado.admin.dto.ReciboDTO;
import com.mercado.admin.entity.Recibo;
import com.mercado.admin.service.ReciboService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> crear(@Valid @RequestBody ReciboDTO dto) {
        try {
            ReciboDTO creado = reciboService.crearRecibo(dto);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear recibo: " + e.getMessage());
        }
    }

    @GetMapping("/puesto/{codigo}")
    public ResponseEntity<List<Recibo>> listarPorPuesto(@PathVariable String codigo) {
        List<Recibo> recibos = reciboService.listarPorPuesto(codigo);

        if (recibos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recibos);
    }
}