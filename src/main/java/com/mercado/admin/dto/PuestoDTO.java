package com.mercado.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record PuestoDTO(
        @NotBlank String codigo,
        @NotBlank String duenoRuc
) {}