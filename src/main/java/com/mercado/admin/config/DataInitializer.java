package com.mercado.admin.config;

import com.mercado.admin.entity.Dueno;
import com.mercado.admin.entity.Puesto;
import com.mercado.admin.repository.DuenoRepository;
import com.mercado.admin.repository.PuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PuestoRepository puestoRepository;
    private final DuenoRepository duenoRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Si ya hay puestos, no hacemos nada (evita duplicados al reiniciar)
        if (puestoRepository.count() > 0) {
            System.out.println("INFO: La base de datos ya contiene puestos. Saltando inicialización.");
            return;
        }

        System.out.println("INICIO: Generando datos iniciales del mercado...");

        // 1. Verificar o crear la Asociación de Comerciantes
        String rucAsociacion = "20201910984";
        Dueno asociacion = duenoRepository.findById(rucAsociacion).orElse(null);

        if (asociacion == null) {
            asociacion = new Dueno();
            asociacion.setDniRuc(rucAsociacion);
            asociacion.setNombreRazonSocial("Asociación de Comerciantes del Mercado");
            asociacion.setCelular("999999999");
            duenoRepository.save(asociacion);
            System.out.println(" - Dueño 'Asociación' creado correctamente.");
        }

        // 2. Crear 100 puestos vinculados a la asociación
        List<Puesto> nuevosPuestos = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Puesto puesto = new Puesto();
            puesto.setCodigo("P-" + i);
            puesto.setDuenoActual(asociacion);
            nuevosPuestos.add(puesto);
        }

        puestoRepository.saveAll(nuevosPuestos);
        System.out.println(" - 100 puestos (P-1 a P-100) creados correctamente.");
        System.out.println("FIN: Inicialización completada.");
    }
}
