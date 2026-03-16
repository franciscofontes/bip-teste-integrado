package com.example.backend.controller;

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
}
