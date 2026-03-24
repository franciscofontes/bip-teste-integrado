package com.example.backend.controller;

import com.example.backend.dto.BeneficioRequestDTO;
import com.example.backend.dto.BeneficioResponseDTO;
import com.example.backend.dto.BeneficioResponseDetailsDTO;
import com.example.backend.dto.TransferDTO;
import com.example.backend.service.BeneficioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@Tag(name = "Beneficios", description = "Gerenciamento de beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os beneficios", description = "Retorna uma lista de beneficios cadastrados")
    @GetMapping
    public List<BeneficioResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<BeneficioResponseDTO>> findByPage(
            @RequestParam(value = "number", defaultValue = "0") Integer number,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<BeneficioResponseDTO> pageBeneficios = service.findByPage(number, size, orderBy, direction);
        return ResponseEntity.ok().body(pageBeneficios);
    }

    @Operation(summary = "Listar beneficio pelo identificador", description = "Retorna beneficio cadastrado")
    @GetMapping("/{id}")
    public BeneficioResponseDetailsDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Criar beneficio", description = "Cria um novo beneficio")
    @PostMapping
    public void create(@RequestBody @Valid BeneficioRequestDTO dto) {
        service.create(dto);
    }

    @Operation(summary = "Atualizar beneficio", description = "Atualiza um beneficio")
    @PutMapping("/{id}")
    public BeneficioResponseDTO update(@PathVariable Long id, @RequestBody @Valid BeneficioRequestDTO dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Deletar beneficio", description = "Remove um beneficio")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Transferência de beneficio", description = "Transfere uma certa quantidade de um beneficio para outro")
    @PostMapping("/transfer")
    public void transfer(@RequestBody @Valid TransferDTO dto) {
        service.transfer(dto);
    }
}
