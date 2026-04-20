package com.mercado.admin.controller;

import com.mercado.admin.dto.DuenoDTO;
import com.mercado.admin.entity.Dueno;
import com.mercado.admin.repository.DuenoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Unidad 2.3.3 y 2.5: Spring Web MVC y RESTful
 * @RestController combina @Controller y @ResponseBody.
 * Indica que todos los métodos devolverán datos serializados (JSON) en lugar de vistas HTML.
 */
@RestController
@RequestMapping("/api/duenos")
@RequiredArgsConstructor
public class DuenoController {

    private final DuenoRepository duenoRepository;

    @GetMapping
    public ResponseEntity<List<Dueno>> listar() {
        return ResponseEntity.ok(duenoRepository.findAll());
    }

    @GetMapping("/{dniRuc}")
    public ResponseEntity<Dueno> buscar(@PathVariable String dniRuc) {
        return duenoRepository.findById(dniRuc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody DuenoDTO dto) {
        if (duenoRepository.existsById(dto.dniRuc())) {
            return ResponseEntity.badRequest().body("El DNI/RUC ya existe");
        }

        Dueno dueno = new Dueno();
        dueno.setDniRuc(dto.dniRuc());
        dueno.setNombreRazonSocial(dto.nombreRazonSocial());
        dueno.setCelular(dto.celular());

        Dueno guardado = duenoRepository.save(dueno);
        return ResponseEntity.status(201).body(guardado);
    }

    @PutMapping("/{dniRuc}")
    public ResponseEntity<?> actualizar(@PathVariable String dniRuc, @Valid @RequestBody DuenoDTO dto) {
        return duenoRepository.findById(dniRuc)
                .map(existing -> {
                    existing.setNombreRazonSocial(dto.nombreRazonSocial());
                    existing.setCelular(dto.celular());
                    return ResponseEntity.ok(duenoRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dniRuc}")
    public ResponseEntity<Void> eliminar(@PathVariable String dniRuc) {
        if (duenoRepository.existsById(dniRuc)) {
            duenoRepository.deleteById(dniRuc);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}