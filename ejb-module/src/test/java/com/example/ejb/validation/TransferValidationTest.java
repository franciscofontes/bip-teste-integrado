package com.example.ejb.validation;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.utils.MessageUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferValidationTest {

    @Test
    void shouldThrowExceptionWhenSameIds() {
        var fromId = 1L;
        var toId = 1L;
        var transferValidation = new TransferValidation();

        var exception = assertThrows(BeneficioException.class, () -> transferValidation.verifySameId(fromId, toId));

        assertEquals(MessageUtils.TRANSFER_SAME_IDS, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        var funds = new BigDecimal("0");
        var amount = new BigDecimal("100.00");
        var transferValidation = new TransferValidation();

        var exception = assertThrows(BeneficioException.class, () -> transferValidation.verifyInsufficientFunds(funds, amount));

        assertEquals(MessageUtils.TRANSFER_INSUFFICIENT_FUNDS, exception.getMessage());
    }
}