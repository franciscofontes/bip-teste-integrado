package com.example.backend.client;

import com.example.backend.dto.BeneficioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "beneficios", url = "${api.beneficios.url}")
public interface BeneficioClient {

    @GetMapping()
    List<BeneficioResponseDTO> findAll();
}
