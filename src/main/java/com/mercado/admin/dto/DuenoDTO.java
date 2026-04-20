package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Unidad 2.5: Spring RESTful
 * Los DTOs desacoplan la API de la estructura de la base de datos.
 * Usamos 'record' de Java 17+ para inmutabilidad y código limpio.
 */
public record DuenoDTO(
        @NotBlank(message = "El DNI/RUC es obligatorio")
        @Pattern(regexp = "^\\d{8}$|^\\d{11}$", message = "DNI debe tener 8 dígitos o RUC 11 dígitos")
        String dniRuc,

        @NotBlank(message = "El nombre o razón social es obligatorio")
        @Size(max = 150)
        String nombreRazonSocial,

        @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener 9 dígitos")
        String celular
) {}