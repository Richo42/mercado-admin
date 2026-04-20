package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record PagoTotalRequestDTO(
        @NotBlank String puestoCodigo,
        @NotBlank String adminRegistro // Nombre o ID del admin que registra
) {}