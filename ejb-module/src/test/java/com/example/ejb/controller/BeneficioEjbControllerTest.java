package com.example.ejb.controller;

import com.example.ejb.dto.BeneficioResponseDTO;
import com.example.ejb.dto.BeneficioResponseDetailsDTO;
import com.example.ejb.dto.TransferDTO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import com.example.ejb.utils.MessageUtils;
import com.example.ejb.utils.MockUtils;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbControllerTest {

    @Mock
    private BeneficioEjbService service;

    @InjectMocks
    private BeneficioEjbController controller;

    @Test
    void shouldReturnBeneficiosWhenFindAll() {

        var beneficios = List.of(MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class));

        when(service.findAll()).thenReturn(beneficios);

        var result = controller.findAll();

        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllWhenFail() {
        when(service.findAll()).thenThrow(BeneficioException.class);

        assertThrows(BeneficioException.class, () -> controller.findAll());
    }

    @Test
    public void shouldReturnBeneficioWhenIdExists() {

        var id = 1L;
        var beneficio = MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class);
        var beneficioResponseDetailsDTO = MockUtils.fromFile("beneficio-response-details-dto-valid-01.json", BeneficioResponseDetailsDTO.class);

        when(service.findById(id)).thenReturn(beneficio);

        var response = controller.findById(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(beneficioResponseDetailsDTO.nome(), ((BeneficioResponseDetailsDTO) response.getEntity()).nome());
    }

    @Test
    void shouldReturn404WhenIdNotExists() {
        var id = 500L;
        var msg = "Beneficio não cadastrado";

        when(service.findById(id)).thenThrow(new BeneficioException(404, msg));

        BeneficioException exception = assertThrows(BeneficioException.class, () -> controller.findById(id));

        assertEquals(404, exception.getStatus());
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void shouldTransferBeneficioWhenValid() {

        var fromId = 1L;
        var toId = 2L;
        var amount = new BigDecimal("500.00");

        controller.transfer(new TransferDTO(fromId, toId, amount));

        verify(service, times(1)).transfer(fromId, toId, amount);
    }

    @Test
    public void shouldReturn422WhenTransferWithSameIds() {

        var fromId = 1L;
        var toId = 1L;
        var amount = new BigDecimal("2000.00");
        var transferDTO = new TransferDTO(fromId, toId, amount);
        var msg = MessageUtils.TRANSFER_SAME_IDS;

        when(controller.transfer(transferDTO)).thenThrow(new BeneficioException(422, msg));

        BeneficioException exception = assertThrows(BeneficioException.class, () -> controller.transfer(transferDTO));

        assertEquals(422, exception.getStatus());
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void shouldReturn422WhenTransferWithInsufficientFunds() {

        var fromId = 1L;
        var toId = 2L;
        var amount = new BigDecimal("2000.00");
        var transferDTO = new TransferDTO(fromId, toId, amount);
        var msg = MessageUtils.TRANSFER_INSUFFICIENT_FUNDS;

        when(controller.transfer(transferDTO)).thenThrow(new BeneficioException(422, msg));

        BeneficioException exception = assertThrows(BeneficioException.class, () -> controller.transfer(transferDTO));

        assertEquals(422, exception.getStatus());
        assertEquals(msg, exception.getMessage());
    }
}