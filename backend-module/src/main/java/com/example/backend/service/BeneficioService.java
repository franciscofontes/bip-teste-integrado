package com.example.backend.service;

import com.example.backend.client.BeneficioClient;
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
        try {
            return client.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao integrar com API de beneficios");
        }
    }
}
