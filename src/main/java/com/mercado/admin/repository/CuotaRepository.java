package com.mercado.admin.repository;

import com.mercado.admin.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    /**
     * Unidad 2.2.2: Consultas por método derivado
     * Genera automáticamente:
     * SELECT c FROM Cuota c WHERE c.puesto.codigo = :puestoCodigo AND c.estado = :estado
     */
    List<Cuota> findByPuestoCodigoAndEstado(String puestoCodigo, String estado);

    /**
     * Unidad 2.2.2 / 2.2.3: Validación de duplicados
     * Devuelve true si ya existe una cuota para ese puesto, tipo de recibo y periodo.
     * Spring Data recorre la relación: cuota -> recibo -> tipo
     */
    boolean existsByPuestoCodigoAndReciboTipoAndPeriodo(String puestoCodigo, String reciboTipo, String periodo);

    /**
     * Unidad 2.2.2: Seguimiento de carga
     * Devuelve la lista de códigos de puesto que ya tienen asignado un tipo de recibo en un periodo.
     * Útil para que el frontend muestre qué puestos ya fueron cubiertos.
     */
    List<String> findDistinctPuestoCodigoByPeriodoAndReciboTipo(String periodo, String reciboTipo);
}