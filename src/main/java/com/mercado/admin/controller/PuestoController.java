package com.mercado.admin.controller;

import com.mercado.admin.dto.PuestoDTO;
import com.mercado.admin.entity.Dueno;
import com.mercado.admin.entity.Puesto;
import com.mercado.admin.repository.DuenoRepository;
import com.mercado.admin.repository.PuestoRepository;
import com.mercado.admin.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puestos")
@RequiredArgsConstructor
public class PuestoController {

    private final PuestoRepository puestoRepository;
    private final DuenoRepository duenoRepository;
    private final TransferenciaService transferenciaService;

    @GetMapping
    public ResponseEntity<List<Puesto>> listar() {
        return ResponseEntity.ok(puestoRepository.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Puesto> buscar(@PathVariable String codigo) {
        return puestoRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PuestoDTO dto) {
        if (dto.codigo() == null || dto.duenoRuc() == null) {
            return ResponseEntity.badRequest().body("Código y dueño son obligatorios");
        }
        if (puestoRepository.existsById(dto.codigo())) {
            return ResponseEntity.badRequest().body("El código de puesto ya existe");
        }

        Dueno dueno = duenoRepository.findById(dto.duenoRuc())
                .orElseThrow(() -> new RuntimeException("Dueño no encontrado: " + dto.duenoRuc()));

        Puesto puesto = new Puesto();
        puesto.setCodigo(dto.codigo());
        puesto.setDuenoActual(dueno);

        return ResponseEntity.status(201).body(puestoRepository.save(puesto));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        if (puestoRepository.existsById(codigo)) {
            puestoRepository.deleteById(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint específico para transferencias.
     * Se usa POST porque representa una acción de negocio con validaciones,
     * no una simple actualización de campo.
     */
    @PostMapping("/{codigo}/transferir")
    public ResponseEntity<String> transferir(
            @PathVariable String codigo,
            @RequestParam String nuevoDuenoRuc) {

        String mensaje = transferenciaService.transferirPuesto(codigo, nuevoDuenoRuc);

        if (mensaje.startsWith("No se puede transferir")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }
}