package com.example.ejb.dto;

import java.math.BigDecimal;

public record BeneficioResponseDTO(Long id, String nome, BigDecimal valor, boolean ativo) {
}
