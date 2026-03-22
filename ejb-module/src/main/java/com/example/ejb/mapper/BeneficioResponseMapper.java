package com.example.ejb.mapper;

import com.example.ejb.dto.BeneficioResponseDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioResponseMapper implements Mapper<Beneficio, BeneficioResponseDTO> {

    @Override
    public Beneficio toEntity(BeneficioResponseDTO beneficioResponseDTO) {
        return new Beneficio(beneficioResponseDTO.id(), beneficioResponseDTO.nome(), beneficioResponseDTO.valor(), beneficioResponseDTO.ativo());
    }

    @Override
    public BeneficioResponseDTO toDTO(Beneficio beneficio) {
        return new BeneficioResponseDTO(beneficio.getId(), beneficio.getNome(), beneficio.getValor(), beneficio.isAtivo());
    }
}
