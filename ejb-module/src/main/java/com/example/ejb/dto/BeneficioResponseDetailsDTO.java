package com.example.ejb.dto;

import java.math.BigDecimal;

public record BeneficioResponseDetailsDTO(Long id, String nome, String descricao, BigDecimal valor, boolean ativo, Long versao) {
}
