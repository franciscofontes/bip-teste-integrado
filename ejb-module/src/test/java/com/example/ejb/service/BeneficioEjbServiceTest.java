package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import com.example.ejb.utils.MockUtils;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbServiceTest {

    @Mock
    private BeneficioDAO dao;

    @InjectMocks
    private BeneficioEjbService service;

    @Test
    void shouldReturnBeneficiosWhenFindAll() {

        var beneficios = List.of(MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class));

        when(dao.findAll()).thenReturn(beneficios);

        var result = service.findAll();

        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllWhenFail() {
        when(dao.findAll()).thenThrow(BeneficioException.class);

        assertThrows(BeneficioException.class, () -> service.findAll());
    }

    @Test
    void shouldReturnBeneficioWhenIdExists() {
        var beneficioOptional = Optional.of(MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class));
        var id = 1L;

        when(dao.findById(id)).thenReturn(beneficioOptional);

        var beneficio = service.findById(id);

        assertEquals(id, (long) beneficio.getId());
    }

    @Test
    void shouldThrowExceptionWhenIdNotExists() {
        var id = 3L;

        when(dao.findById(id)).thenThrow(BeneficioException.class);

        assertThrows(BeneficioException.class, () -> service.findById(id));
    }

    @Test
    void shouldTransferBeneficiosWhenValid() {
        var from = MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class);
        var to = MockUtils.fromFile("beneficio-valid-02.json", Beneficio.class);
        var fromId = 1L;
        var toId = 2L;
        var amount = new BigDecimal("500.00");

        when(dao.findById(fromId)).thenReturn(Optional.of(from));
        when(dao.findById(toId)).thenReturn(Optional.of(to));

        service.transfer(fromId, toId, amount);

        verify(dao, times(1)).updateBeneficios(from, to);
    }

    @Test
    void shouldNotTransferBeneficiosWhenInsufficientFunds() {
        var from = MockUtils.fromFile("beneficio-insufficient-funds.json", Beneficio.class);
        var to = MockUtils.fromFile("beneficio-valid-02.json", Beneficio.class);
        var fromId = 1L;
        var toId = 2L;
        var amount = new BigDecimal("500.00");

        when(dao.findById(fromId)).thenReturn(Optional.of(from));
        when(dao.findById(toId)).thenReturn(Optional.of(to));

        assertThrows(BeneficioException.class, () -> service.transfer(fromId, toId, amount));
    }
}