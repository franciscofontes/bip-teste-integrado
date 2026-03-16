package com.example.backend.service;

import com.example.backend.client.BeneficioClient;
import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioClient client;

    public BeneficioService(BeneficioClient client) {
        this.client = client;
    }

    public List<BeneficioResponseDTO> findAll() {
        return client.findAll();
    }

    public BeneficioResponseDTO findById(Long id) {
        return client.findById(id);
    }

    public void create(BeneficioRequestDTO dto) {
        client.create(dto);
    }

    public BeneficioResponseDTO update(Long id, BeneficioRequestDTO dto) {
        return client.update(id, dto);
    }

    public void delete(Long id) {
        client.delete(id);
    }
}
