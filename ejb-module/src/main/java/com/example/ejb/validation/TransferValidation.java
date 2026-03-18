package com.example.ejb.validation;

import com.example.ejb.exception.BeneficioException;

public class TransferValidation {

    public void verify(Long fromId, Long toId) {
        if (fromId.equals(toId)) {
            throw new BeneficioException(422, "Identificador de origem deve ser diferente do de destino");
        }
    }
}
