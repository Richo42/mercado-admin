package com.mercado.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "PUESTO")
@NoArgsConstructor
@AllArgsConstructor
public class Puesto {

    @Id
    @Column(name = "codigo", length = 10, nullable = false)
    private String codigo;

    /**
     * Unidad 1.1.3: Relación @ManyToOne
     * Muchos puestos pueden pertenecer a un solo dueño.
     * @JoinColumn especifica la columna foránea en la tabla PUESTO.
     * nullable = false cumple la regla de negocio: un puesto siempre tiene dueño.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dueno_actual_dni_ruc", nullable = false)
    private Dueno duenoActual;
}