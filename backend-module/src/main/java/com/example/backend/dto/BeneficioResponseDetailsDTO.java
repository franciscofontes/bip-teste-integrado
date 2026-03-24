package com.example.backend.dto;

import java.math.BigDecimal;

public record BeneficioResponseDetailsDTO(Long id, String nome, String descricao, BigDecimal valor, boolean ativo, Long versao) {
}
