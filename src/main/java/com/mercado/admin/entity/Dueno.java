package com.mercado.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Unidad 1.1.3: Entidades JPA
 * La anotación @Entity indica que esta clase representa una tabla en la base de datos.
 * @Table permite especificar el nombre exacto de la tabla.
 */
@Data
@Entity
@Table(name = "DUENO")
@NoArgsConstructor
@AllArgsConstructor
public class Dueno {

    /**
     * @Id marca este campo como la clave primaria.
     * No usamos @GeneratedValue porque el DNI/RUC es un identificador natural
     * que ingresa el usuario, no un número secuencial generado por la BD.
     */
    @Id
    @Column(name = "dni_ruc", length = 11, nullable = false)
    private String dniRuc;

    @Column(name = "nombre_razon_social", nullable = false)
    private String nombreRazonSocial;

    @Column(length = 20)
    private String celular;
}