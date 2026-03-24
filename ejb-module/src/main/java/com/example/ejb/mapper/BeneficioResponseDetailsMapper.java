package com.example.ejb.mapper;

import com.example.ejb.dto.BeneficioResponseDetailsDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioResponseDetailsMapper implements Mapper<Beneficio, BeneficioResponseDetailsDTO> {

    @Override
    public Beneficio toEntity(BeneficioResponseDetailsDTO beneficioResponseDetailsDTO) {
        return new Beneficio(beneficioResponseDetailsDTO.id(), beneficioResponseDetailsDTO.nome(), beneficioResponseDetailsDTO.valor(), beneficioResponseDetailsDTO.ativo());
    }

    @Override
    public BeneficioResponseDetailsDTO toDTO(Beneficio beneficio) {
        return new BeneficioResponseDetailsDTO(beneficio.getId(), beneficio.getNome(), beneficio.getDescricao(), beneficio.getValor(), beneficio.isAtivo(), beneficio.getVersion());
    }
}
