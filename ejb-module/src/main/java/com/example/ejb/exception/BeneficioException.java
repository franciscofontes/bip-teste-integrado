package com.example.ejb.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BeneficioException extends RuntimeException {

    private final int status;

    public BeneficioException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
