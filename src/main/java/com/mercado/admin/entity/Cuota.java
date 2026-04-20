package com.mercado.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUOTA")
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relación con Puesto: Una cuota pertenece a un solo puesto.
     * FetchType.EAGER se usa aquí para facilitar la consulta de deudas por puesto
     * sin necesidad de consultas adicionales en la capa de servicio.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_codigo", nullable = false)
    private Puesto puesto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recibo_id", nullable = false)
    private Recibo recibo;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "fecha_registro_pago")
    private LocalDateTime fechaRegistroPago;

    @Column(name = "admin_registro")
    private String adminRegistro;

    @Column(nullable = false)
    private String periodo;
}