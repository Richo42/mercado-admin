package com.mercado.admin.repository;

import com.mercado.admin.dto.CuotaDTO;
import com.mercado.admin.dto.ReporteRecaudacionDTO;
import com.mercado.admin.entity.Cuota;
import com.mercado.admin.entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    // 1. ESTE ES EL QUE BUSCAS (Para CuotaService)
    // Spring genera este método automáticamente por el nombre
    List<Cuota> findByPuestoCodigo(String puestoCodigo);

    // 2. ESTE ES PARA PAGOS Y TRANSFERENCIAS (PagoService / TransferenciaService)
    List<Cuota> findByPuestoCodigoAndEstado(String puestoCodigo, String estado);

    // 3. ESTE ES PARA RECIBOS (ReciboService)
    @Query("SELECT DISTINCT c.recibo FROM Cuota c WHERE c.puesto.codigo = :codigo")
    List<Recibo> findRecibosByPuestoCodigo(@Param("codigo") String codigo);

    // 4. ESTE ES PARA EL CONTROLADOR DE CUOTAS (CuotaController - DTO Optimizado)
    @Query("SELECT new com.mercado.admin.dto.CuotaDTO(" +
            "c.id, c.puesto.codigo, r.tipo, r.categoria, c.monto, c.estado, c.periodo, " +
            "c.fechaPago, c.adminRegistro) " +
            "FROM Cuota c JOIN c.recibo r " +
            "WHERE c.puesto.codigo = :codigo")
    List<CuotaDTO> findDetalleCuotasByPuestoCodigo(@Param("codigo") String codigo);

    // 5. VALIDACIÓN DE DUPLICADOS
    boolean existsByPuestoCodigoAndReciboTipoAndPeriodo(String puestoCodigo, String reciboTipo, String periodo);

    // 6. SEGUIMIENTO DE CARGA
    List<String> findDistinctPuestoCodigoByPeriodoAndReciboTipo(String periodo, String reciboTipo);

    // 7.
    @Query(value = "SELECT " +
            "c.periodo, " +
            "SUM(c.monto), " +
            "SUM(CASE WHEN c.estado = 'PAGADO' THEN c.monto ELSE 0 END), " +
            "SUM(CASE WHEN c.estado = 'PENDIENTE' THEN c.monto ELSE 0 END), " +
            "COUNT(DISTINCT CASE WHEN c.estado = 'PENDIENTE' THEN c.puesto_codigo END) " +
            "FROM cuota c " +
            "WHERE (:desde IS NULL OR c.periodo >= :desde) " +
            "  AND (:hasta IS NULL OR c.periodo <= :hasta) " +
            "GROUP BY c.periodo " +
            "ORDER BY c.periodo DESC",
            nativeQuery = true)
    List<ReporteRecaudacionDTO> obtenerRecaudacionPorPeriodo(
            @Param("desde") String desde,
            @Param("hasta") String hasta
    );

    @Query("SELECT COALESCE(SUM(c.monto), 0) FROM Cuota c WHERE c.puesto.codigo = :puestoCodigo AND c.estado = 'PENDIENTE'")
    BigDecimal findDeudaPendienteByPuesto(@Param("puestoCodigo") String puestoCodigo);
}