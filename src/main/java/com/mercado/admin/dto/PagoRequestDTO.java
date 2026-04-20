package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record PagoRequestDTO(
        @NotBlank String puestoCodigo,
        @NotBlank String adminRegistro
) {}