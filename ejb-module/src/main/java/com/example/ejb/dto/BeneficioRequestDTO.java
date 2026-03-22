package com.example.ejb.dto;

import java.math.BigDecimal;

public record BeneficioRequestDTO(String nome, String descricao, BigDecimal valor, boolean ativo) {
}
