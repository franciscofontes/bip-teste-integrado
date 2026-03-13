package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbServiceTest {

    @Mock
    private BeneficioDAO dao;

    @InjectMocks
    private BeneficioEjbService service;

    @Test
    void shouldReturnBeneficioWhenIdExists() {
        var beneficioOptional = Optional.of(new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("1000.00"), true));

        when(dao.findById(1L)).thenReturn(beneficioOptional);

        var beneficio = service.findById(1L);

        assertEquals(1L, (long) beneficio.getId());
    }

    @Test
    void shouldThrowExceptionWhenIdNotExists() {
        when(dao.findById(3L)).thenThrow(BeneficioException.class);

        assertThrows(BeneficioException.class, () -> service.findById(3L));
    }

    @Test
    void shouldTransferBeneficiosWhenValid() {
        var from = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("1000.00"), true);
        var to = new Beneficio(2L, "Beneficio B", "Descrição B", new BigDecimal("500.00"), true);
        var amount =  new BigDecimal("500.00");

        when(dao.findById(1L)).thenReturn(Optional.of(from));
        when(dao.findById(2L)).thenReturn(Optional.of(to));

        service.transfer(1L, 2L, amount);

        verify(dao, times(1)).updateBeneficios(from, to);
    }

    @Test
    void shouldNotTransferBeneficiosWhenInsufficientFunds() {
        var from = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("0"), true);
        var to = new Beneficio(2L, "Beneficio B", "Descrição B", new BigDecimal("500.00"), true);
        var amount =  new BigDecimal("500.00");

        when(dao.findById(1L)).thenReturn(Optional.of(from));
        when(dao.findById(2L)).thenReturn(Optional.of(to));

        assertThrows(BeneficioException.class, () -> service.transfer(1L, 2L, amount));
    }
}