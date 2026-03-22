package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BeneficioRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100)
        String nome,

        String descricao,

        @PositiveOrZero(message = "Valor deve ser zero ou maior")
        BigDecimal valor,

        boolean ativo) {
}
