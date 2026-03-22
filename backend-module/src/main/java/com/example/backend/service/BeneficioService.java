package com.example.backend.service;

import com.example.backend.client.BeneficioClient;
import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import com.example.backend.dto.TransferDTO;
import org.springframework.data.domain.Page;
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

    public Page<BeneficioResponseDTO> findByPage(Integer number, Integer size, String orderBy, String direction) {
        return client.findByPage(number, size, orderBy, direction);
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

    public void transfer(TransferDTO dto) {
        client.transfer(dto);
    }
}
