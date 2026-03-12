package com.example.ejb.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BeneficioException extends RuntimeException {
    
    public BeneficioException(String message) { 
        super(message); 
    }
}
