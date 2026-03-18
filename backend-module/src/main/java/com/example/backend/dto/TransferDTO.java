package com.example.backend.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferDTO(
        @Positive(message = "Identificador de origem deve ser maior que zero")
        Long fromId,

        @Positive(message = "Identificador de destino deve ser maior que zero")
        Long toId,

        @Positive(message = "Quantidade deve ser maior que zero")
        BigDecimal amount) {
}
