package com.example.backend.controller;

import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import com.example.backend.service.BeneficioService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public List<BeneficioResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public BeneficioResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public void create(@RequestBody BeneficioRequestDTO dto) {
        service.create(dto);
    }

    @PutMapping("/{id}")
    public BeneficioResponseDTO update(@PathVariable Long id, @RequestBody BeneficioRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}
