package com.mercado.admin.repository;

import com.mercado.admin.entity.CorrelativoComprobante;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CorrelativoRepository extends JpaRepository<CorrelativoComprobante, Integer> {

    /*El @Lock(PESSIMISTIC_WRITE) traduce internamente a SELECT ... FOR UPDATE.
    Mientras esta transacción no termine, ninguna otra podrá leer ni modificar la misma fila.*/
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CorrelativoComprobante c WHERE c.anio = :anio")
    Optional<CorrelativoComprobante> findByAnioForUpdate(@Param("anio") Integer anio);
}