package com.example.ejb.mapper;

import com.example.ejb.dto.BeneficioRequestDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioRequestMapper implements Mapper<Beneficio, BeneficioRequestDTO>{

    @Override
    public Beneficio toEntity(BeneficioRequestDTO beneficioRequestDTO) {
        return new Beneficio(beneficioRequestDTO.nome(), beneficioRequestDTO.descricao(), beneficioRequestDTO.valor(), beneficioRequestDTO.ativo());
    }

    @Override
    public BeneficioRequestDTO toDTO(Beneficio beneficio) {
        return new BeneficioRequestDTO(beneficio.getNome(), beneficio.getDescricao(), beneficio.getValor(), beneficio.isAtivo());
    }
}
