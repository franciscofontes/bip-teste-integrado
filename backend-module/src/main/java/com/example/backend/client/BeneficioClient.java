package com.example.backend.client;

import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "beneficios-service", url = "${api.beneficios.url}")
public interface BeneficioClient {

    @GetMapping()
    List<BeneficioResponseDTO> findAll();

    @GetMapping("{id}")
    BeneficioResponseDTO findById(@PathVariable("id") Long id);

    @PostMapping()
    void create(@RequestBody BeneficioRequestDTO dto);

    @PutMapping("{id}")
    BeneficioResponseDTO update(@PathVariable("id") Long id, @RequestBody BeneficioRequestDTO dto);

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") Long id);
}
