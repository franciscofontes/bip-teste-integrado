package com.example.ejb.validation;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.utils.MessageUtils;

import java.math.BigDecimal;

public class TransferValidation {

    public void verifySameId(Long fromId, Long toId) {
        if (fromId.equals(toId)) {
            throw new BeneficioException(422, MessageUtils.TRANSFER_SAME_IDS);
        }
    }

    public void verifyInsufficientFunds(BigDecimal funds, BigDecimal amount) {
        if (amount.compareTo(funds) > 0) {
            throw new BeneficioException(422, MessageUtils.TRANSFER_INSUFFICIENT_FUNDS);
        }
    }
}
