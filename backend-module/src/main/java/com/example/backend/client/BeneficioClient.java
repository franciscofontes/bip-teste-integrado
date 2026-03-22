package com.example.backend.client;

import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import com.example.backend.dto.TransferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "beneficios-service", url = "${api.beneficios.url}")
public interface BeneficioClient {

    @GetMapping()
    List<BeneficioResponseDTO> findAll();

    @GetMapping("/page")
    Page<BeneficioResponseDTO> findByPage(
            @RequestParam(value = "number", defaultValue = "0") Integer number,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction);

    @GetMapping("{id}")
    BeneficioResponseDTO findById(@PathVariable("id") Long id);

    @PostMapping()
    void create(@RequestBody BeneficioRequestDTO dto);

    @PutMapping("{id}")
    BeneficioResponseDTO update(@PathVariable("id") Long id, @RequestBody BeneficioRequestDTO dto);

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") Long id);

    @PostMapping("/transfer")
    void transfer(@RequestBody TransferDTO dto);
}
