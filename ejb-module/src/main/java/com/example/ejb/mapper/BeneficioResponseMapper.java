package com.example.ejb.mapper;

import com.example.ejb.dto.BeneficioResponseDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioResponseMapper implements Mapper<Beneficio, BeneficioResponseDTO> {

    @Override
    public Beneficio toEntity(BeneficioResponseDTO beneficioResponseDTO) {
        return new Beneficio(beneficioResponseDTO.nome(), beneficioResponseDTO.valor());
    }

    @Override
    public BeneficioResponseDTO toDTO(Beneficio beneficio) {
        return new BeneficioResponseDTO(beneficio.getNome(), beneficio.getValor());
    }
}
